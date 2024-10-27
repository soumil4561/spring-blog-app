package com.blog.BlogApp.services;

import com.blog.BlogApp.models.User;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User registerUser(User user){
        //1. first we check if same username already registered
        User check = userRepository.findByUsername(user.getUsername());
        if(check!=null){
            throw new UsernameNotFoundException("username already registered");
        }
        //2. next if no clash, we bcrypt the password
        user.setPassword(encoder.encode(user.getPassword()));
        //3. save user
        return userRepository.save(user);
    }

    public String loginUser(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "No vro...";
    }
}
