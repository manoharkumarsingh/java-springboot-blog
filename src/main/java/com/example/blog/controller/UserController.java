package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.BlogEntryService;
import com.example.blog.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.blog.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogEntryService blogEntryService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser == null ||
                !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        String token = jwtService.generateToken(existingUser.getEmail());

        return ResponseEntity.ok(token);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if(user.getEmail().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email is empty");
        }else if(user.getPassword().isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password is empty");
        }

        if(userService.findByEmail(user.getEmail())!=null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable ObjectId id) {
        if(userService.findById(id) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        if(userService.findById(id) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if(userService.findById(user.getId()) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(userService.updateUser(user));
    }



}
