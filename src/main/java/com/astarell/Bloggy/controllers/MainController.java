package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.DTOs.PostDTO;
import com.astarell.Bloggy.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/")
@PropertySource("classpath:bloggy-main.properties")
public class MainController {

    @Value("${recent_posts_quantity}")
    private int recentPostsQuantity;

    @Value("${main_page_posts_quantity}")
    private int mainPostsQuantity;

    private final PostService postService;

    @Autowired
    public MainController(PostService postService){
        this.postService = postService;
    }

    @ModelAttribute(name = "posts")
    public Collection<PostDTO> mainPosts(){
        return postService.getRecentPosts(mainPostsQuantity);
    }

    @ModelAttribute(name = "recentPosts")
    public Collection<PostDTO> recentPosts(){
        return postService.getRecentPosts(recentPostsQuantity);
    }

    @GetMapping("/")
    public String mainPage(){
        return "main-pages/main";
    }

    @GetMapping("/search")
    public String mainSearchPage(@RequestParam(required = false) String searchWord,
                                 Model model){

        List<PostDTO> searchMatches = postService.findPostsByKeyWord(searchWord);

        if(!searchMatches.isEmpty()){
            model.addAttribute("posts", searchMatches);
        }
        else{
            ObjectError error = new ObjectError(
                    "PostsNotFoundError",
                    "Cannot find posts, recent ones are shown");

            model.addAttribute("PostsNotFoundError", error);
            model.addAttribute("posts", postService.getPosts(mainPostsQuantity));
        }

        return "main-pages/main";
    }

    @GetMapping("/details/{postId}")
    public String postDetails(@PathVariable Long postId, Model model){

        if(postService.existsById(postId)){
            model.addAttribute("post", postService.findByIdDTOsPurpose(postId));
            return "main-pages/main-details";
        }

        return "redirect:/";
    }
}