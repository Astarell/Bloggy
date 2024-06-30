package com.astarell.Bloggy.services;

import com.astarell.Bloggy.DTOs.PostDTO;
import com.astarell.Bloggy.models.Post;
import com.astarell.Bloggy.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepo;
    private final CommentService commentService;
    @Autowired
    public PostService(PostRepository postRepo, CommentService commentService){
        this.postRepo = postRepo;
        this.commentService = commentService;
    }

    public Post findByIdServicePurpose(Long postId){
        return postRepo.findById(postId).get();
    }

    public PostDTO findByIdDTOsPurpose(Long postId){
        return entityToPostDTO(postRepo.findById(postId).get());
    }

    public boolean existsById(Long postId){
        return postRepo.existsById(postId);
    }

    public List<PostDTO> getRecentPosts(int quantity){
        return listOfEntityToListOfDTO(postRepo.getRecentPosts(quantity));
    }

    public List<PostDTO> findPostsByKeyWord(String keyword){
        return listOfEntityToListOfDTO(postRepo.findPostsByKeyWord(keyword));
    }

    public List<PostDTO> getPosts(int quantity){
        return listOfEntityToListOfDTO(postRepo.getPosts(quantity));
    }

    public List<PostDTO> findPostsByOwnerUsername(String username){
        return listOfEntityToListOfDTO(postRepo.findPostsByOwnerUsername(username));
    }

    public void deleteById(Long postId){
        postRepo.deleteById(postId);
    }

    public void update(Long postId, Post patch){
        postRepo.update(postId, patch);
    }

    public void save(Post post){
        postRepo.save(post);
    }

    public PostDTO entityToPostDTO(Post entity){
        return new PostDTO(entity.getId(), entity.getTitle(),
                entity.getAnnouncement(), entity.getFullText(),
                entity.timeToFormat(), entity.getPostOwner(),
                entity.getComments().stream().map(commentService::entityToCommentDTO).collect(Collectors.toList()));
    }

    public List<PostDTO> listOfEntityToListOfDTO(Collection<Post> entities){
        return entities.stream()
                .map(this::entityToPostDTO)
                .collect(Collectors.toList());
    }
}
