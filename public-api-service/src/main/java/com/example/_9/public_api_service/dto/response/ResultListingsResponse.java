package com.example._9.public_api_service.dto.response;

import java.util.List;

public record ResultListingsResponse(boolean result, List<ListingResponse> listings) {
}
