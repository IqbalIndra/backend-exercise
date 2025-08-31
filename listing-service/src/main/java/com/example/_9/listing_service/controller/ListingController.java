package com.example._9.listing_service.controller;

import com.example._9.listing_service.dto.response.ListingResponse;
import com.example._9.listing_service.exception.ValidationErrorException;
import com.example._9.listing_service.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listings")
@AllArgsConstructor
public class ListingController {
    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<?> findListings(@RequestParam(value = "page_num", defaultValue = "0") Integer pageNum,
                                          @RequestParam(value = "page_size", defaultValue = "5") Integer pageSize,
                                          @RequestParam(value = "user_id", required = false) String userId){
        Integer id;
        try {
            id = Integer.parseInt(userId);
        }catch (NumberFormatException ex){
            throw new ValidationErrorException("Invalid Input user_id : "+userId);
        }


        List<?> listings = listingService.getListing(pageNum,pageSize,id);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("listings",listings);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addListing(@RequestParam(value = "price") Integer price,
                                        @RequestParam(value = "listing_type") String listingType,
                                        @RequestParam(value = "user_id") String userId){

        Integer id;
        try {
            id = Integer.parseInt(userId);
        }catch (NumberFormatException ex){
            throw new ValidationErrorException("Invalid Input user_id : "+userId);
        }


        ListingResponse listing = listingService.addListing(id,listingType,price);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("listings",listing);

        return ResponseEntity.ok(result);
    }

}
