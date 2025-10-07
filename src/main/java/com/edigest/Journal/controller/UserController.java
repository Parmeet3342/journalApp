package com.edigest.Journal.controller;

import com.edigest.Journal.entity.User;
import com.edigest.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User userDetails = userService.findByUserName(name);
        if(userDetails == null){
            return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
        }
        userDetails.setUserName(user.getUserName());
       if(!user.getPassword().isEmpty()){
           userDetails.setPassword(user.getPassword());
       }
        userService.saveNewUser(userDetails);
        return new ResponseEntity<>(userDetails, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId myId){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            userService.deleteUserById(myId,userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
