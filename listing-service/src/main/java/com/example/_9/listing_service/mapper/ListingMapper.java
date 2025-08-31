package com.example._9.listing_service.mapper;

import com.example._9.listing_service.dto.response.ListingResponse;
import com.example._9.listing_service.model.Listing;
import org.springframework.stereotype.Component;

@Component
public class ListingMapper {

    public ListingResponse mapToListingResponse(Listing listing){
        return new ListingResponse(listing.getId(), listing.getUserId(), listing.getListingType().getType(),
                listing.getPrice(),listing.getCreatedAt(), listing.getUpdatedAt());
    }
}
