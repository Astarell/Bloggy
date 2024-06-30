package com.astarell.Bloggy.customdaos;

import com.astarell.Bloggy.models.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class PostDaoBasicImpl implements PostDaoBasic<Post> {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void update(Long id, Post patchObject) {
        Post curPost = em.find(Post.class, id);
        if(!curPost.getTitle().equals(patchObject.getTitle())){
            curPost.setTitle(patchObject.getTitle());
        }
        if(!curPost.getAnnouncement().equals(patchObject.getAnnouncement())){
            curPost.setAnnouncement(patchObject.getAnnouncement());
        }
        if(!curPost.getFullText().equals(patchObject.getFullText())){
            curPost.setFullText(patchObject.getFullText());
        }

        em.merge(curPost);
    }
    @Override
    public List<Post> getRecentPosts(int quantity){

        List<Post> posts = em.createQuery("from Post order by createdAt desc", Post.class)
                .setMaxResults(quantity)
                .getResultList();

        return posts;
    }

    @Override
    public List<Post> findPostsByKeyWord(String searchWord) {
        List<Post> titlePosts = em.createQuery("from Post where lower(title) ilike concat('%', :wordToFind ,'%')", Post.class)
                .setParameter("wordToFind", searchWord)
                .getResultList();

        List<Post> announcementPosts = em.createQuery("from Post where lower(announcement) ilike concat('%', :wordToFind ,'%')", Post.class)
                .setParameter("wordToFind", searchWord)
                .getResultList();

        List<Post> allFindings = new ArrayList<>();
        allFindings.addAll(titlePosts);
        allFindings.addAll(announcementPosts);

        return allFindings;
    }

    @Override
    public List<Post> findPostsByOwnerUsername(String username) {

        List<Post> posts = em.createQuery("from Post where postOwner.username = :username", Post.class)
                .setParameter("username", username)
                .getResultList();

        return posts;
    }

    @Override
    public List<Post> getPosts(int quantity){

        List<Post> posts = em.createQuery("from Post", Post.class)
                .setMaxResults(quantity)
                .getResultList();

        return posts;
    }
}
