package com.astarell.Bloggy.repositories;

import com.astarell.Bloggy.customdaos.PostDaoBasic;
import com.astarell.Bloggy.models.Account;
import com.astarell.Bloggy.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>, PostDaoBasic<Post> {
}
