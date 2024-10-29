package com.blog.BlogApp.exeptions;

public class ArticleNotFoundException extends Throwable {
    public ArticleNotFoundException(String s) {
        super(s);
    }
}
