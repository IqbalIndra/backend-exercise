package com.example._9.listing_service.service;

import com.example._9.listing_service.dto.response.ListingResponse;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    public List<?> getListing(Integer pageNum, Integer pageSize, Integer userId){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<Listing> pageListing = null;
        if(ObjectUtils.isEmpty(userId)){
            pageListing = listingRepository.findAll(pageable);
        }else{
            pageListing = listingRepository.findByUserId(userId, pageable);
        }

        return pageListing.get()
                .map(listingMapper::mapToListingResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ListingResponse addListing(Integer userId, String listingType, Integer price){
        if(!ListingType.isValid(listingType))
            throw new ValidationErrorException(String.format("Type not found for : %s" , listingType));
        if(userId <= 0 || price <= 0)
            throw new ValidationErrorException("Must be greater than 0");


        long microSecond = ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now()) * (long) 1e6;
        Listing listing = Listing.of()
                .userId(userId)
                .listingType(ListingType.of(listingType))
                .price(price)
                .createdAt((int)microSecond)
                .updatedAt((int) microSecond)
                .build();
        return listingMapper.mapToListingResponse(listingRepository.save(listing));
    }

}
