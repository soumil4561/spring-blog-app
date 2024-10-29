package com.blog.BlogApp.initializers;

import com.blog.BlogApp.models.Role;
import com.blog.BlogApp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class RoleInitializer implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.count() == 0) {
            // Add default roles if the table is empty
            List<Role> roles = Arrays.asList(
                    new Role("ROLE_ADMIN"),
                    new Role("ROLE_MODERATOR"),
                    new Role("ROLE_USER"),
                    new Role("ROLE_GUEST")
            );
            roleRepository.saveAll(roles);
            System.out.println("Roles initialized successfully.");
        } else {
            System.out.println("Roles already exist. Initialization skipped.");
        }
    }
}

