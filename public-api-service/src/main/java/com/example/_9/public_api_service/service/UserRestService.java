package com.example._9.public_api_service.service;

import com.example._9.public_api_service.dto.request.UserRequest;
import com.example._9.public_api_service.dto.response.*;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @CircuitBreaker(name = "userCircuitBreaker", fallbackMethod = "fallbackUsersRest")
    @Retry(name = "userRetry")
    public List<UserResponse> callGetUsersRestPagination(Integer pageNum, Integer pageSize){
        Map<String,Object> request = new HashMap<>();
        request.put("page_num", pageNum);
        request.put("page_size", pageSize);


        ResultUsersResponse usersResponse = restTemplate.getForObject("http://localhost:8887/users",ResultUsersResponse.class , request);
        return usersResponse.users();
    }

    @CircuitBreaker(name = "userCircuitBreaker", fallbackMethod = "fallbackUserRest")
    @Retry(name = "userRetry")
    public UserResponse callAddUserRest(UserRequest userRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("name", userRequest.getName());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<ResultUserResponse> response = restTemplate.postForEntity( "http://localhost:8887/users", request , ResultUserResponse.class );
        return response.getBody().user();
    }

    private UserResponse fallbackUserRest(Throwable t) {
        log.error("Error When Hit User service : {}" , t.getMessage());
        return new UserResponse(-1,null,-1,-1);
    }

    private List<UserResponse> fallbackUsersRest(Throwable t) {
        log.error("Error When Hit List User service : {}" , t.getMessage());
        return List.of();
    }
}
