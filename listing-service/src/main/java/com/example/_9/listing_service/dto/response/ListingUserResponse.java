package com.example._9.listing_service.dto.response;

public record ListingUserResponse(Integer id, String listingType, Integer price,
                                  Integer createdAt, Integer updatedAt,UserResponse user) {
}
