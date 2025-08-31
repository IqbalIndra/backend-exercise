package com.example._9.listing_service.model.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.ObjectUtils;

@Converter(autoApply = true)
public class ListingTypeConverter implements AttributeConverter<ListingType,String> {
    @Override
    public String convertToDatabaseColumn(ListingType listingType) {
        if(listingType == null)
            return null;

        switch (listingType){
            case SALE : return "sale";
            case RENT : return "rent";
            default: throw new IllegalArgumentException("Unknown Listing Type for : "+listingType);
        }
    }

    @Override
    public ListingType convertToEntityAttribute(String s) {
        if (ObjectUtils.isEmpty(s)) {
            return null;
        }
        switch (s) {
            case "sale": return ListingType.SALE;
            case "rent": return ListingType.RENT;
            default: throw new IllegalArgumentException("Unknown database data: " + s);
        }
    }
}
