package com.communityhelp.repository;

import com.communityhelp.model.Request;
import com.communityhelp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {

    List<Request> findByPostedBy(User user);
    List<Request> findByPostedByOrAcceptedBy(User postedBy, User acceptedBy);
    List<Request> findByStatus(String status);
    List<Request> findByCategory(String category);
}


