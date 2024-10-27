package com.blog.BlogApp.services;

import com.blog.BlogApp.models.User;
import com.blog.BlogApp.models.UserPrincipal;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user==null){
            System.out.println("no user found");
            throw new UsernameNotFoundException("No user found");
        }
        return new UserPrincipal(user);
    }
}
