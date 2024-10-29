package com.blog.BlogApp.repositories;

import com.blog.BlogApp.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.author.id = :id")
    List<Article> findArticlesByAuthorID(Integer id);
}
