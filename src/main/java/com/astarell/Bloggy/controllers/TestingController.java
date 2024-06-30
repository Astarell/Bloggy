package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.DTOs.AccountDTO;
import com.astarell.Bloggy.DTOs.PostDTO;
import com.astarell.Bloggy.services.AccountService;
import com.astarell.Bloggy.services.CommentService;
import com.astarell.Bloggy.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/test-api")
public class TestingController {
    private final AccountService accountService;
    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public TestingController(AccountService accountService,
                             CommentService commentService,
                             PostService postService){
        this.accountService = accountService;
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("/account")
    public ResponseEntity<Collection<AccountDTO>> getAllAccounts(){
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<Collection<PostDTO>> getAllPosts(){
        return new ResponseEntity<>(postService.getRecentPosts(12), HttpStatus.OK);
    }
}
