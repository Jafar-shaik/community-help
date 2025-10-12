package com.communityhelp.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @NotNull
    private String username;
    @Valid
    private String email;
    @NotNull
    private String password;
    private List<String > roles = new ArrayList<>(); // "REQUESTER" or "HELPER"
    private String phone;
    @NotNull
    private String location;
    @NotNull
    private String city;
}

