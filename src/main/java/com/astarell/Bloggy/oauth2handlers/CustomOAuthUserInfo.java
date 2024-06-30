package com.astarell.Bloggy.oauth2handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomOAuthUserInfo {
    private Integer id;
    private String email;
    private String name;
    private String imageUrl;
}
