package com.communityhelp.controller;

import com.communityhelp.model.Request;
import com.communityhelp.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request, Authentication authentication) {
        Request created = requestService.createRequest(request, authentication);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Request>> getMyRequests(Authentication authentication) {
        return ResponseEntity.ok(requestService.getMyRequests(authentication));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Request> acceptRequest(@PathVariable String id, Authentication authentication) {
        return ResponseEntity.ok(requestService.acceptRequest(id, authentication));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Request> completeRequest(@PathVariable String id, Authentication authentication) {
        return ResponseEntity.ok(requestService.completeRequest(id, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable String id) {
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
