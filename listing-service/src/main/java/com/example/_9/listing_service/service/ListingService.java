package com.example._9.listing_service.service;

import com.example._9.listing_service.dto.response.ListingResponse;
import com.example._9.listing_service.dto.response.UserResponse;
import com.example._9.listing_service.exception.ValidationErrorException;
import com.example._9.listing_service.mapper.ListingMapper;
import com.example._9.listing_service.model.Listing;
import com.example._9.listing_service.model.enums.ListingType;
import com.example._9.listing_service.repository.ListingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRestService userRestService;
    private final ListingMapper listingMapper;

    public List<?> getListing(Integer pageNum, Integer pageSize, Integer userId){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<Listing> pageListing = null;
        if(ObjectUtils.isEmpty(userId)){
            pageListing = listingRepository.findAll(pageable);
            return pageListing.get()
                    .map(listingMapper::mapToListingResponse)
                    .collect(Collectors.toList());
        }else{
            pageListing = listingRepository.findByUserId(userId, pageable);
            UserResponse userResponse = userRestService.callGetUserRest(userId);
            return pageListing.get()
                    .map(listing -> listingMapper.mapToListingUserResponse(listing,userResponse))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public ListingResponse addListing(Integer userId, String listingType, Integer price){
        if(!ListingType.isValid(listingType))
            throw new ValidationErrorException(String.format("Type not found for : %s" , listingType));
        if(userId <= 0 || price <= 0)
            throw new ValidationErrorException("Must be greater than 0");

        UserResponse userResponse = userRestService.callGetUserRest(userId);
        if(userResponse.id() < 0)
            throw new ValidationErrorException("User not found with id : "+userId);

        long instantTimeMillis = Instant.now().toEpochMilli();
        Listing listing = Listing.of()
                .userId(userResponse.id())
                .listingType(ListingType.of(listingType))
                .price(price)
                .createdAt((int)instantTimeMillis)
                .updatedAt((int) instantTimeMillis)
                .build();
        return listingMapper.mapToListingResponse(listingRepository.save(listing));
    }

}
