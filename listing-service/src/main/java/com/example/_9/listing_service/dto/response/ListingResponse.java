package com.example._9.listing_service.dto.response;

public record ListingResponse (Integer id, Integer userId,
                               String listingType,Integer price,
                               Integer createdAt, Integer updateAt){
}
