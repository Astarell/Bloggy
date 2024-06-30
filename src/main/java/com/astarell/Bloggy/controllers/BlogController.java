package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.DTOs.PostDTO;
import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.models.Post;
import com.astarell.Bloggy.models.PostForm;
import com.astarell.Bloggy.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;
    @Autowired
    public BlogController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public String blogMain(@AuthenticationPrincipal Account account, Model model){
        Iterable<PostDTO> posts = postService.findPostsByOwnerUsername(account.getUsername());

        model.addAttribute("posts", posts);
        return "blog-pages/blog-main";
    }

    @GetMapping("/add")
    public String addPostPage(Model model){
        model.addAttribute("postForm", new PostForm());
        return "blog-pages/blog-add";
    }

    @GetMapping("/{postId}")
    public String getPostDetails(@PathVariable Long postId, Model model){
        if(!postService.existsById(postId)){
            return "redirect:/blog";
        }

        model.addAttribute("post", postService.findByIdDTOsPurpose(postId));
        return "blog-pages/blog-details";
    }

    @GetMapping("/edit/{postId}")
    public String editPage(@PathVariable Long postId, Model model){
        if(!postService.existsById(postId)){
            return "redirect:/blog";
        }

        model.addAttribute("post", postService.findByIdDTOsPurpose(postId));
        return "blog-pages/blog-edit";
    }

    @PostMapping("/delete/{postId}")
    @PreAuthorize("#ownerUsername == authentication.name")
    @Transactional
    public String deletePost(@PathVariable Long postId,
                             @RequestParam String ownerUsername){
        if(!postService.existsById(postId)){
            return "redirect:/blog";
        }

        postService.deleteById(postId);
        return "redirect:/blog";
    }

    @PostMapping("/edit")
    @PreAuthorize("#ownerUsername == authentication.name")
    @Transactional
    public String processEditing(@RequestParam Long postId,
                                 @RequestParam String ownerUsername,
                                 @ModelAttribute(name = "post") Post patchPost){

        postService.update(postId, patchPost);

        return "redirect:/blog";
    }

    @PostMapping("/add")
    @Transactional
    public String processPost(@Valid @ModelAttribute(name = "postForm") PostForm form,
                              @AuthenticationPrincipal Account account,
                              BindingResult errors){
        if(errors.hasErrors()){
            return "blog-pages/blog-add";
        }

        Post post = form.toPost();
        post.setPostOwner(account);

        account.getAccountPosts().add(post);

        postService.save(post);
        return "redirect:/";
    }
}
