package com.blog.BlogApp.controllers;

import com.blog.BlogApp.exeptions.ActionAlreadyCompletedException;
import com.blog.BlogApp.models.User;
import com.blog.BlogApp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard(Authentication authentication) {
        return new ResponseEntity<>("Welcome to the admin dashboard...",HttpStatus.OK);
    }

    @PutMapping("/make-moderator/{id}")
    public ResponseEntity<?> promoteToModerator(@PathVariable Integer id){
        try{
            adminService.promoteToModerator(id);
        }
        catch (Exception e){
            return new ResponseEntity<>("Problem on our side",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ActionAlreadyCompletedException e) {
            return new ResponseEntity<>("User is already a moderator", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("remove-moderator/{id}")
    public ResponseEntity<?> removeModerator(@PathVariable Integer id){
        try{
            adminService.demoteFromModerator(id);
        }
        catch (Exception e){
            return new ResponseEntity<>("Problem on our side",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ActionAlreadyCompletedException e) {
            return new ResponseEntity<>("User had no moderator role to begin with...", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
