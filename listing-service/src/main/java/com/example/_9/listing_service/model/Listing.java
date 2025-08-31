package com.example._9.listing_service.model;

import com.example._9.listing_service.model.enums.ListingType;
import com.example._9.listing_service.model.enums.ListingTypeConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "listing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Convert(converter = ListingTypeConverter.class)
    private ListingType listingType;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "createdAt", nullable = false)
    private Integer createdAt;
    @Column(name = "updatedAt", nullable = false)
    private Integer updatedAt;
}
