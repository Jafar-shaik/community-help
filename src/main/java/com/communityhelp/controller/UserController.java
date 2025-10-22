package com.communityhelp.controller;

import com.communityhelp.model.User;
import com.communityhelp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable String id,
            @RequestBody User updatedUser,
            Authentication authentication) {

        String loggedInUsername = authentication.getName();

        // Optional: ensure the user updates only their own account
        if (!loggedInUsername.equals(updatedUser.getUsername())) {
            return ResponseEntity.status(403).build();
        }

        User user = userService.updateUserById(id, updatedUser);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        String loggedInUsername = authentication.getName();
        userService.deleteUser(loggedInUsername);
        return ResponseEntity.ok("User deleted successfully");
    }
}

