package com.example._9.user_service.repository;

import com.example._9.user_service.model.User;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private TestEntityManager testEntityManager;

    @Test
    public void should_be_empty_users_if_no_repository(){
        Iterable users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    public void should_be_save_user(){
        User user = userRepository.save(User.of()
                        .name("test")
                        .createdAt((int)1l)
                        .updatedAt((int)1l)
                .build());
        assertEquals(user.getName(),"test");
        assertEquals(user.getCreatedAt(),1);
        assertEquals(user.getUpdatedAt(),1);
    }

    @Test
    public void should_find_users_with_pagination(){
        User user1 = User.of()
                        .name("tes1")
                .createdAt((int)1l)
                .updatedAt((int)1l)
                .build();
        testEntityManager.persist(user1);

        User user2 = User.of()
                .name("tes2")
                .createdAt((int)1l)
                .updatedAt((int)2l)
                .build();
        testEntityManager.persist(user2);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<User> pageListing = userRepository.findAll(pageable);

        assertEquals(pageListing.getNumberOfElements(), 2);
        assertThat(pageListing.getContent()).contains(user2,user1);

    }

    @Test
    public void should_find_user_with_id(){
        User user1 = User.of()
                .name("tes1")
                .createdAt((int)1l)
                .updatedAt((int)1l)
                .build();
        user1 = testEntityManager.persist(user1);

        Optional<User> user = userRepository.findById(user1.getId());
        assertTrue(user.isPresent());
    }

    @Test
    public void should_find_empty_user_with_id(){
        Optional<User> user = userRepository.findById(99);
        assertFalse(user.isPresent());
    }
}
