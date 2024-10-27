package com.blog.BlogApp.services;

import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository repository;

    //crud of articles
    //create
    public void createArticle(Article article){
        repository.save(article);
    }

    public List<Article> getALlArticles(){
        return repository.findAll();
    }

    public Article getArticle(Long id){
        return repository.findById(id).orElse(new Article("Not Found", "No Content"));
    }

    public void updateArticle(Long id, Article article){
        Article existingArticle = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));

        // Update the fields
        existingArticle.setTitle(article.getTitle());
        existingArticle.setContent(article.getContent());
        existingArticle.setLastEditedDate(new Date()); // Automatically update the edit date

        // Save the updated article
        repository.save(existingArticle);
    }

    public void deleteArticle(Long id){
        repository.deleteById(id);
    }

}
