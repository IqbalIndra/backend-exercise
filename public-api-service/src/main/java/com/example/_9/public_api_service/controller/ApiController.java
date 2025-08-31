package com.example._9.public_api_service.controller;

import com.example._9.public_api_service.dto.request.ListingRequest;
import com.example._9.public_api_service.dto.request.UserRequest;
import com.example._9.public_api_service.dto.response.ListingResponse;
import com.example._9.public_api_service.dto.response.UserResponse;
import com.example._9.public_api_service.service.ListingService;
import com.example._9.public_api_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public-api")
@AllArgsConstructor
public class ApiController {
    private final ListingService listingService;
    private final UserService userService;

    @GetMapping("/listings")
    public ResponseEntity<?> getListings(@RequestParam("page_num") Integer pageNum,
                                         @RequestParam("page_size") Integer pageSize,
                                         @RequestParam(value = "user_id", required = false) String userId){
        List<?> response = listingService.getListing(pageNum, pageSize, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("listings",response);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/listings")
    public ResponseEntity<?> addListing(@Valid @RequestBody ListingRequest listingRequest){
        ListingResponse response = listingService.addListing(listingRequest);

        return ResponseEntity.ok(Map.of("listing",response));
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse response = userService.addUser(userRequest);
        return ResponseEntity.ok(Map.of("user", response));
    }
}
