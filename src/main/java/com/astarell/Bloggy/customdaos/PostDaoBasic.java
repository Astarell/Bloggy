package com.astarell.Bloggy.customdaos;

import java.util.List;

public interface PostDaoBasic<Post> {
    void update(Long id, Post patchObject);
    List<Post> getPosts(int quantity);
    List<Post> getRecentPosts(int quantity);
    List<Post> findPostsByKeyWord(String title);
    List<Post> findPostsByOwnerUsername(String username);
}
