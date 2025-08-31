package com.example._9.public_api_service.service;

import com.example._9.public_api_service.dto.request.ListingRequest;
import com.example._9.public_api_service.dto.response.ListingResponse;
import com.example._9.public_api_service.dto.response.ResultListingResponse;
import com.example._9.public_api_service.dto.response.ResultListingsResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ListingRestService {
    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "listingCircuitBreaker", fallbackMethod = "fallbackListingsRest")
    @Retry(name = "listingRetry")
    public List<ListingResponse> callGetListingRestPagination(Integer pageNum, Integer pageSize, String id){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8888/listings")
                .queryParam("page_num", pageNum)
                .queryParam("page_size", pageSize);
        if(!ObjectUtils.isEmpty(id)){
            builder.queryParam("user_id",id);
        }


        ResultListingsResponse listingResponse = restTemplate.getForObject(builder.toUriString(),ResultListingsResponse.class);
        return listingResponse.listings();
    }

    @CircuitBreaker(name = "listingCircuitBreaker", fallbackMethod = "fallbackUserRest")
    @Retry(name = "listingRetry")
    public ListingResponse callAddListingRest(ListingRequest listingRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("user_id", String.valueOf(listingRequest.getUserId()));
        map.add("listing_type", listingRequest.getListingType());
        map.add("price", listingRequest.getPrice());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<ResultListingResponse> response = restTemplate.postForEntity( "http://localhost:8888/listings", request , ResultListingResponse.class );
        return response.getBody().listing();
    }

    private List<ListingResponse> fallbackListingsRest(Throwable t) {
        log.error("Error When Hit List Listing service : {}" , t.getMessage());
        return List.of();
    }

    private ListingResponse fallbackListingRest(Throwable t) {
        log.error("Error When Hit Listing service : {}" , t.getMessage());
        return new ListingResponse(-1,-1,"",-1,-1,-1);
    }
}
