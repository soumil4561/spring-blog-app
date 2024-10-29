package com.blog.BlogApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@NoArgsConstructor
@Setter
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Ensure this is Identity strategy
    private Long articleID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEditedDate;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Article(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @PrePersist
    protected void onCreate() {
        dateOfCreation = new Date();
        lastEditedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastEditedDate = new Date();
    }
}
