package com.example._9.public_api_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "this is required")
    private String name;
}
