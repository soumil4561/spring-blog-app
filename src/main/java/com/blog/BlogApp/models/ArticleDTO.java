package com.blog.BlogApp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDTO {
    private Long articleID;
    private String title;
    private String content;
    private String authorName; // Only include the author's name
}