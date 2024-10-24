package com.blog.BlogApp.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
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

    public Article() {
    }

    public void setArticleID(Long articleID) {
        this.articleID = articleID;
    }

    public Long getArticleID() {
        return articleID;
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getLastEditedDate() {
        return lastEditedDate;
    }

    public void setLastEditedDate(Date lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
