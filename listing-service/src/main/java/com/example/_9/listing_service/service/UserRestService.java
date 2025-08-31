package com.example._9.listing_service.service;

import com.example._9.listing_service.dto.response.ResultUserResponse;
import com.example._9.listing_service.dto.response.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class UserRestService {
    private final RestTemplate restTemplate;

    @CircuitBreaker(name = "userCircuitBreaker", fallbackMethod = "fallbackUserRest")
    @Retry(name = "userRetry")
    public UserResponse callGetUserRest(Integer id){
        ResultUserResponse userResponse = restTemplate.getForObject("http://localhost:8887/users/{id}",ResultUserResponse.class , id);
        return userResponse.user();
    }

    private UserResponse fallbackUserRest(Throwable t) {
        log.error("Error When Hit User service : {}" , t.getMessage());
        return new UserResponse(-1,null,-1,-1);
    }
}
