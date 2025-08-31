package com.example._9.public_api_service.service;

import com.example._9.public_api_service.dto.request.UserRequest;
import com.example._9.public_api_service.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRestService userRestService;

    public UserResponse addUser(UserRequest userRequest){
        return userRestService.callAddUserRest(userRequest);
    }

    public List<UserResponse> getAllUsers(Integer pageNum, Integer pageSize){
        return userRestService.callGetUsersRestPagination(pageNum,pageSize);
    }
}
