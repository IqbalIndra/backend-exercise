package com.example._9.public_api_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListingRequest {
    @NotNull(message = "Cannot be null")
    @Min(value = 1)
    @JsonProperty("user_id")
    private Integer userId;
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "This is required")
    @JsonProperty("listing_type")
    private String listingType;
    @NotNull(message = "Cannot be null")
    @Min(value = 1)
    @JsonProperty("price")
    private Integer price;
}
