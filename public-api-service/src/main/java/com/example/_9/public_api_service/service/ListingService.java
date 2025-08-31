package com.example._9.public_api_service.service;

import com.example._9.public_api_service.dto.request.ListingRequest;
import com.example._9.public_api_service.dto.response.ListingResponse;
import com.example._9.public_api_service.dto.response.UserResponse;
import com.example._9.public_api_service.exception.ValidationErrorException;
import com.example._9.public_api_service.mapper.ListingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ListingService {
    private final ListingRestService listingRestService;
    private final UserRestService userRestService;
    private final ListingMapper listingMapper;

    public ListingResponse addListing(ListingRequest listingRequest){
        UserResponse userResponse = userRestService.callGetUserRest(listingRequest.getUserId());
        if(userResponse.id() < 0)
            throw new ValidationErrorException("User not found with id : "+listingRequest.getUserId());

        return listingRestService.callAddListingRest(listingRequest);
    }

    public List<?> getListing(Integer pageNum, Integer pageSize, String userId){
        Integer id;
        if(!ObjectUtils.isEmpty(userId)){
            try{
                id = Integer.parseInt(userId);
            }catch (NumberFormatException ex){
                throw new ValidationErrorException("Invalid user with id : "+userId);
            }
            UserResponse userResponse = userRestService.callGetUserRest(id);
            if(userResponse.id() < 0)
                throw new ValidationErrorException("User not found with id : "+id);

            List<ListingResponse> listingResponses = listingRestService.callGetListingRestPagination(pageNum,pageSize,userId);
            return listingResponses.stream()
                    .map(listingResponse -> listingMapper.mapToListingUserResponse(listingResponse, userResponse))
                    .collect(Collectors.toList());
        }else{
            return listingRestService.callGetListingRestPagination(pageNum,pageSize,userId);
        }

    }
}
