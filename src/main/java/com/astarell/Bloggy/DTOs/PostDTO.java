package com.astarell.Bloggy.DTOs;

import com.astarell.Bloggy.models.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String announcement;
    private String fullText;
    private String createdAt;
    private Account account;
    private Collection<CommentDTO> commentDTOS;
}
