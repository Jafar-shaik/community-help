package com.communityhelp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    private String id;

    @DBRef
    private Request requestId;

    @DBRef
    private User senderId;

    @DBRef
    private User receiverId;

    private String message;
    private Date timestamp;
}

