package com.blog.BlogApp.services;

import com.blog.BlogApp.exeptions.ActionAlreadyCompletedException;
import com.blog.BlogApp.exeptions.UserNotFoundException;
import com.blog.BlogApp.models.Role;
import com.blog.BlogApp.models.User;
import com.blog.BlogApp.repositories.RoleRepository;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void promoteToModerator(Integer userId) throws ActionAlreadyCompletedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user with provided id found"));
        //if user is already a mod
        Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR");
        if(user.getRoles().contains(moderatorRole)){
            throw new ActionAlreadyCompletedException("User is already a moderator");
        }
        user.getRoles().add(moderatorRole);
        userRepository.save(user);
    }

    public void demoteFromModerator(Integer userId) throws Exception, ActionAlreadyCompletedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR");
        //check if user is already moderator
        if(user.getRoles().contains(moderatorRole)){
            user.getRoles().remove(moderatorRole);
            userRepository.save(user);
        }
        else throw new ActionAlreadyCompletedException("User was not a moderator.");
    }
}