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

    public User updateUser(User updatedUser){
        User foundUser = userRepository.findByUsername(updatedUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found found with name " + updatedUser.getUsername()));
        foundUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        foundUser.setUsername(updatedUser.getUsername());
        foundUser.setCity(updatedUser.getCity());
        foundUser.setEmail(updatedUser.getEmail());
        foundUser.setPhone(updatedUser.getPhone());
        foundUser.setLocation(updatedUser.getLocation());
        return userRepository.save(foundUser);
    }

    public void deleteUser(String username){
        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + username));
        userRepository.deleteById(foundUser.getId());
    }

    public boolean exitsByUserName(String username){
        return userRepository.existsByUsername(username);
    }
}

