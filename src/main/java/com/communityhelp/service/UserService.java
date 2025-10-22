package com.communityhelp.service;

import com.communityhelp.model.User;
import com.communityhelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Collections.addAll(user.getRoles(),"ROLE_REQUESTER","ROLE_HELPER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username){
        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));
        return foundUser;
    }

    public User updateUserById(String id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());
        existing.setCity(updatedUser.getCity());
        existing.setLocation(updatedUser.getLocation());
        return userRepository.save(existing);
    }


    public void deleteUser(String username){
        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));
        userRepository.deleteById(foundUser.getId());
    }

    public boolean exitsByUserName(String username){
        return userRepository.existsByUsername(username);
    }

    public User findByUserId(String userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
}

