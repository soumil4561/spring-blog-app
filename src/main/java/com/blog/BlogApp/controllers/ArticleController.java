package com.blog.BlogApp.controllers;

import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<List<Article>> getArticles(){
        return new ResponseEntity<>(articleService.getALlArticles(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(@RequestBody Article article){
        try{
            articleService.createArticle(article);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/article/{articleID}")
    public ResponseEntity<Article> findArticle(@PathVariable Long articleID){
        Article article = articleService.getArticle(articleID);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PutMapping("/edit/{articleID}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleID, @RequestBody Article article){
        try{
            articleService.updateArticle(articleID,article);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{articleID}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleID){
        try {
            articleService.deleteArticle(articleID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}