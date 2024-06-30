package com.astarell.Bloggy.DTOs;

import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String message;
    private String createdAt;
    private Account account;
    private Post post;
}
