package com.astarell.Bloggy.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
//@Indexed
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String announcement;

    @Column(length = 5000, name = "full_text")
    private String fullText;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @JsonManagedReference(value = "account_posts")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Account postOwner;

    @JsonManagedReference(value = "comments_post")
    @OrderBy("createdAt DESC")
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "post")
    private Collection<Comment> comments;

    public Post(String title, String announcement, String fullText){
        this.title = title;
        this.announcement = announcement;
        this.fullText = fullText;
    }

    @JsonCreator
    public Post(@JsonProperty("id") Long id,
                @JsonProperty("title") String title,
                @JsonProperty("announcement") String announcement,
                @JsonProperty("fullText") String fullText,
                @JsonProperty("postOwner") Account postOwner,
                @JsonProperty("comments") Collection<Comment> comments){
        this.id = id;
        this.title = title;
        this.announcement = announcement;
        this.fullText = fullText;
        this.postOwner = postOwner;
        this.comments = comments;
    }

    public String timeToFormat(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
    }
}
