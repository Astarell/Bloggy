package com.astarell.Bloggy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
//    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String message;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @JsonManagedReference(value = "account_comments")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Account account;

    @JsonBackReference(value = "comments_post")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Post post;

    public Comment(String message, Account account, Post post){
        this.message = message;
        this.account = account;
        this.post = post;
    }

    @JsonCreator
    public Comment(@JsonProperty("id") Long id,
                   @JsonProperty("createdAt") Timestamp createdAt,
                   @JsonProperty("message") String message,
                   @JsonProperty("account") Account account,
                   @JsonProperty("post") Post post){
        this.message = message;
        this.id = id;
        this.createdAt = createdAt;
        this.account = account;
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", account=" + account.getUsername() +
                ", post=" + post.getId() +
                '}';
    }

    public String timeToFormat(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createdAt);
    }
}