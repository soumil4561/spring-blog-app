package com.blog.BlogApp.exeptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
