package com.blog.BlogApp.services;

import com.blog.BlogApp.exeptions.ArticleNotFoundException;
import com.blog.BlogApp.exeptions.UnauthorizedFileUpdateRequestException;
import com.blog.BlogApp.models.Article;
import com.blog.BlogApp.models.ArticleDTO;
import com.blog.BlogApp.models.Role;
import com.blog.BlogApp.models.User;
import com.blog.BlogApp.repositories.ArticleRepository;
import com.blog.BlogApp.repositories.RoleRepository;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private ArticleDTO convertToArticleDTO(Article article){
        ArticleDTO dto = new ArticleDTO();
        dto.setArticleID(article.getArticleID());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        if (article.getAuthor() != null) {
            dto.setAuthorName(article.getAuthor().getUsername()); // Set the author's name
        }
        return dto;
    }

    public void createArticle(Article article, String username){
        User user = userRepository.findByUsername(username);
        if(user==null) throw new UsernameNotFoundException("No such user found");
        article.setAuthor(user);
        articleRepository.save(article);
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOs = new ArrayList<>();

        for (Article article : articles) {
            articleDTOs.add(convertToArticleDTO(article)); // Add the DTO to the list
        }

        return articleDTOs; // Return the list of DTOs
    }

    public List<ArticleDTO> getArticlesByAuthor(Integer id){
        List<Article> articles = articleRepository.findArticlesByAuthorID(id);
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (Article article : articles) {
            articleDTOs.add(convertToArticleDTO(article)); // Add the DTO to the list
        }
        return articleDTOs; // Return the list of DTOs
    }

    public ArticleDTO getArticle(Long id) throws ArticleNotFoundException {
        Article article = articleRepository.findById(id).orElseThrow(()-> new ArticleNotFoundException("Article not Found"));
        return convertToArticleDTO(article);
    }

    public void updateArticle(Long id, Article article, String username) throws UnauthorizedFileUpdateRequestException {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));
        User user = userRepository.findByUsername(username);
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(Objects.equals(existingArticle.getAuthor().getUsername(), user.getUsername()) || user.getRoles().contains(admin)){
            existingArticle.setTitle(article.getTitle());
            existingArticle.setContent(article.getContent());
            existingArticle.setLastEditedDate(new Date());
            articleRepository.save(existingArticle);
        }
        else throw new UnauthorizedFileUpdateRequestException("Can't access this resource with update privilege");
    }

    public void deleteArticle(Long id, String username) throws UnauthorizedFileUpdateRequestException, ArticleNotFoundException {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with ID: " + id));
        User user = userRepository.findByUsername(username);
        Role moderator = roleRepository.findByName("ROLE_MODERATOR");
        Role admin = roleRepository.findByName("ROLE_ADMIN");
        if(Objects.equals(existingArticle.getAuthor().getUsername(), user.getUsername())
                || user.getRoles().contains(moderator)
                || user.getRoles().contains(admin)){
            articleRepository.deleteById(id);
        }
        else throw new UnauthorizedFileUpdateRequestException("Can't access this resource with update privilege");
    }
}