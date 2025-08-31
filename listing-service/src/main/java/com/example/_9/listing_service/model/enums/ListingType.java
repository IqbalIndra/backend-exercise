package com.example._9.listing_service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ListingType {
    RENT("rent"),
    SALE("sale");

    private final String type;

    public static boolean isValid(String value) {
        return Arrays.stream(ListingType.values())
                .anyMatch(listingType -> listingType.getType().equalsIgnoreCase(value));
    }
}
