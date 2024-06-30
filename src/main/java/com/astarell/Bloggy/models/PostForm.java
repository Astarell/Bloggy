package com.astarell.Bloggy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {
//    @Length(min = 20, message = "Title should have more than 20 symbols")
//    @Length(max = 200, message = "Title should be less than 200 symbols")
//    @NotEmpty(message = "Title field shouldn't be empty")
    private String title;

//    @Length(min = 20, message = "Announcement should have more than 20 symbols")
//    @Length(max = 200, message = "Announcement should be less than 200 symbols")
//    @NotEmpty(message = "Announcement field shouldn't be empty")
    private String announcement;

//    @Length(min = 200, message = "Text should have more than 200 symbols")
//    @Length(max = 1500, message = "Text should be less than 1500 symbols")
//    @NotEmpty(message = "Text field shouldn't be empty")
    private String fullText;
    public Post toPost(){
        return new Post(title, announcement, fullText);
    }
}
