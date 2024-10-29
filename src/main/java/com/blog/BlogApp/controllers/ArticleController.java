package com.blog.BlogApp.controllers;

import com.blog.BlogApp.exeptions.ArticleNotFoundException;
import com.blog.BlogApp.exeptions.UnauthorizedFileUpdateRequestException;
import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.models.ArticleDTO;
import com.blog.BlogApp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<List<ArticleDTO>> getArticles(){
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/get-articles-by-author/{id}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByAuthor(@PathVariable Integer id){
        return new ResponseEntity<>(articleService.getArticlesByAuthor(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(@RequestBody Article article, Authentication authentication){
        try{
            articleService.createArticle(article,authentication.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<?> findArticle(@PathVariable Long articleID) throws ArticleNotFoundException {
        try{
            ArticleDTO article = articleService.getArticle(articleID);
            return new ResponseEntity<>(article, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{articleID}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleID, @RequestBody Article article, Authentication authentication){
        try{
            articleService.updateArticle(articleID,article, authentication.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnauthorizedFileUpdateRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete/{articleID}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleID, Authentication authentication){
        try {
            articleService.deleteArticle(articleID, authentication.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ArticleNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnauthorizedFileUpdateRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}