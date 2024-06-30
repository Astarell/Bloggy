package com.astarell.Bloggy.DTOs;

import com.astarell.Bloggy.models.Comment;
import com.astarell.Bloggy.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String username;
    private String email;
    private Collection<Post> accountPosts;
    private Collection<Comment> comments;
    private Collection<? extends GrantedAuthority> authorities;
}
