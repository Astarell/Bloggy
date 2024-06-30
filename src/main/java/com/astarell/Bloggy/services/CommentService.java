package com.astarell.Bloggy.services;

import com.astarell.Bloggy.DTOs.CommentDTO;
import com.astarell.Bloggy.models.Comment;
import com.astarell.Bloggy.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    @Autowired
    public CommentService(CommentRepository commentRepo){
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment){
        commentRepo.save(comment);
    }

    public CommentDTO entityToCommentDTO(Comment entity){
        return new CommentDTO(entity.getId(), entity.getMessage(), entity.timeToFormat(),
                entity.getAccount(), entity.getPost());
    }
}
