package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.models.Comment;
import com.astarell.Bloggy.models.Post;
import com.astarell.Bloggy.services.CommentService;
import com.astarell.Bloggy.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
@RequestMapping(value = "/api-comments")
public class MessageController {
    private PostService postService;
    private CommentService commentService;
    @Autowired
    public MessageController(PostService postService,
                             CommentService commentService){
        this.postService = postService;
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/comment/{postId}")
    public String addComment(@RequestParam String message,
                           @PathVariable Long postId,
                           @AuthenticationPrincipal Account account){

        if(postService.existsById(postId)){
            Post curPost = postService.findByIdServicePurpose(postId);
            Comment newComment = new Comment(message, account, curPost);
            commentService.save(newComment);
        }

        return "redirect:/details/" + postId;
    }
}
