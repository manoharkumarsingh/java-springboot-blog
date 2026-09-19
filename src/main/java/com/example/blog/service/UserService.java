package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteById(ObjectId id) {
        userRepository.deleteById(id);
        return true;
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
