package com.example._9.listing_service.repository;

import com.example._9.listing_service.model.Listing;
import com.example._9.listing_service.model.enums.ListingType;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class ListingRepositoryTest {
    @Autowired
    private  ListingRepository listingRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void should_save_a_listing() {
        Listing listing = listingRepository.save(
                Listing. of()
                        .userId(1)
                        .listingType(ListingType.RENT)
                        .price(200)
                        .createdAt((int) 1l)
                        .updatedAt((int) 1l)
                        .build()
        );

        assertEquals(listing.getId() , 1);
        assertEquals(listing.getListingType() , ListingType.RENT);
        assertEquals(listing.getPrice() , 200);
        assertEquals(listing.getCreatedAt() , (int)1l);
        assertEquals(listing.getUpdatedAt() , (int)1l);
    }

    @Test
    public void should_find_no_listings_if_repository_is_empty() {
        Iterable tutorials = listingRepository.findAll();

        assertThat(tutorials).isEmpty();
    }

    @Test
    public void should_find_listings_with_pagination(){
        Instant instant = Instant.now(); // Or retrieved from entity
        long milliseconds = instant.toEpochMilli();

        Listing listing1 = Listing.of()
                .userId(1)
                .listingType(ListingType.RENT)
                .price(5000)
                .createdAt((int) 1l)
                .updatedAt((int) milliseconds)
                .build();
        testEntityManager.persist(listing1);

        Listing listing2 = Listing.of()
                .userId(1)
                .listingType(ListingType.SALE)
                .price(5000)
                .createdAt((int) 2l)
                .updatedAt((int) milliseconds)
                .build();
        testEntityManager.persist(listing2);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<Listing> pageListing = listingRepository.findAll(pageable);

        assertEquals(pageListing.getNumberOfElements(), 2);
        assertThat(pageListing.getContent()).contains(listing1,listing2);

    }

    @Test
    public void should_find_listings_by_user_id_with_pagination(){
        Instant instant = Instant.now(); // Or retrieved from entity
        long milliseconds = instant.toEpochMilli();

        Listing listing1 = Listing.of()
                .userId(1)
                .listingType(ListingType.RENT)
                .price(5000)
                .createdAt((int) 1l)
                .updatedAt((int) milliseconds)
                .build();
        testEntityManager.persist(listing1);

        Listing listing2 = Listing.of()
                .userId(2)
                .listingType(ListingType.SALE)
                .price(5000)
                .createdAt((int) 2l)
                .updatedAt((int) milliseconds)
                .build();
        testEntityManager.persist(listing2);

        Pageable pageable = PageRequest.of(0, 5,Sort.by("createdAt").descending());
        Page<Listing> pageListing = listingRepository.findByUserId(1,pageable);

        assertEquals(pageListing.getNumberOfElements(), 1);
        assertThat(pageListing.getContent()).contains(listing1);

    }
}
