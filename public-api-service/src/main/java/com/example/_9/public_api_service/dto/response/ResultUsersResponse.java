package com.example._9.public_api_service.dto.response;


import java.util.List;

public record ResultUsersResponse(boolean result , List<UserResponse> users){
}
