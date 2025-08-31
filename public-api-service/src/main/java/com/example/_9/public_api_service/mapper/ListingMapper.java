package com.example._9.public_api_service.mapper;

import com.example._9.public_api_service.dto.response.ListingResponse;
import com.example._9.public_api_service.dto.response.ListingUserResponse;
import com.example._9.public_api_service.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class ListingMapper {
    public ListingUserResponse mapToListingUserResponse(ListingResponse listingResponse, UserResponse userResponse){
        return new ListingUserResponse(listingResponse.id(), listingResponse.listingType(),
                listingResponse.price(), listingResponse.createdAt(), listingResponse.updateAt(),
                userResponse);
    }
}
