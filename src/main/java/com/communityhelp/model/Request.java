package com.communityhelp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

    @Id
    private String id;

    private String title;
    private String description;
    private String category;
    private String location;

    private String status;

    @DBRef
    private User postedBy;

    @DBRef
    private User acceptedBy;

    private Date postedDate;
    private Date completedDate;
}

