package com.blog.BlogApp.controllers;

import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
public class HomeController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public List<Article> getArticles(){
        return articleService.getALlArticles();
    }
}
