package com.communityhelp.service;

import com.communityhelp.model.Request;
import com.communityhelp.model.User;
import com.communityhelp.repository.RequestRepository;
import com.communityhelp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public Request createRequest(Request request, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found: " + username)
        );

        request.setPostedBy(user);
        request.setStatus("OPEN");
        request.setPostedDate(new Date());

        return requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> getMyRequests(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return requestRepository.findByPostedBy(user);
    }

    public Request acceptRequest(String requestId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setAcceptedBy(user);
        request.setStatus("ACCEPTED");

        return requestRepository.save(request);
    }

    public Request completeRequest(String requestId, Authentication authentication) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("COMPLETED");
        request.setCompletedDate(new Date());

        return requestRepository.save(request);
    }

    public Optional<Request> getRequestById(String id) {
        return requestRepository.findById(id);
    }
}

