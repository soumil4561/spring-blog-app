package com.blog.BlogApp.initializers;

import com.blog.BlogApp.models.Role;
import com.blog.BlogApp.models.User;
import com.blog.BlogApp.repositories.RoleRepository;
import com.blog.BlogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN.USERNAME}")
    private String adminUsername;

    @Value("${ADMIN.PASSWORD}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if any admin user exists
        User adminUser = userRepository.findByUsername("admin");
        if (adminUser == null) {
            // Fetch the ROLE_ADMIN role
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            // Create the admin user with default credentials
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));  // Set a default password
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
            System.out.println("Admin user initialized successfully with username: 'admin' and password: 'admin'.");
        } else {
            System.out.println("Admin user already exists. Initialization skipped.");
        }
    }
}