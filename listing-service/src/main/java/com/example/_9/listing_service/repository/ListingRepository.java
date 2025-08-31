package com.example._9.listing_service.repository;

import com.example._9.listing_service.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing,Integer> {
    Page<Listing> findByUserId(Integer userId, Pageable pageable);
}
