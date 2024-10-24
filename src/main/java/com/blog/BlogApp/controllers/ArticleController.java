package com.blog.BlogApp.controllers;

import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/article/create-article")
    public void createArticle(@RequestBody Article article){
        articleService.createArticle(article);
    }

    @GetMapping("/article/{articleID}")
    public Article findArticle(@PathVariable Long articleID){
        return articleService.getArticle(articleID);
    }

    @PutMapping("/edit/{articleID}")
    public void updateArticle(@PathVariable Long articleID, @RequestBody Article article){
        articleService.updateArticle(articleID, article);
    }

    @DeleteMapping("/article/{articleID}")
    public void deleteArticle(@PathVariable Long articleID){
        articleService.deleteArticle(articleID);
    }

}