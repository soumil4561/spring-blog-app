package com.blog.BlogApp.services;

import com.blog.BlogApp.exeptions.ActionAlreadyCompletedException;
import com.blog.BlogApp.models.Role;
import com.blog.BlogApp.models.User;
import com.blog.BlogApp.repositories.RoleRepository;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    public User registerUser(User user) throws ActionAlreadyCompletedException {
        //1. first we check if same username already registered
        User check = userRepository.findByUsername(user.getUsername());
        if(check!=null){
            throw new ActionAlreadyCompletedException("username already registered");
        }
        //2. next if no clash, we bcrypt the password
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(userRole);
        //3. save user
        return userRepository.save(user);
    }

    public String loginUser(User user) throws AuthenticationCredentialsNotFoundException{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        else throw new AuthenticationCredentialsNotFoundException("Wrong Credentials.");
    }
}
