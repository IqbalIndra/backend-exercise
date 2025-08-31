package com.example._9.user_service.service;

import com.example._9.user_service.dto.response.UserResponse;
import com.example._9.user_service.exception.ValidationErrorException;
import com.example._9.user_service.model.User;
import com.example._9.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> findAll(Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<User> pageList = userRepository.findAll(pageable);

        return pageList.get()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer id){
        if(ObjectUtils.isEmpty(id))
            throw new ValidationErrorException(String.format("Id is null : %d", id));

        return userRepository.findById(id)
                .map(user -> new UserResponse(user.getId(),user.getName(), user.getCreatedAt(), user.getUpdatedAt()))
                .orElseThrow(() -> new ValidationErrorException("User not found with id : "+id));
    }

    public UserResponse addUser(String name){
        Optional<User> userOptional = userRepository.findByNameIgnoreCase(name);
        if(userOptional.isPresent())
            throw new ValidationErrorException(String.format("User already exist with name : %s", name));

        long microSecond = ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now()) * (long) 1e6;

        User user = User.of()
                .name(name)
                .createdAt((int)microSecond)
                .updatedAt((int)microSecond)
                .build();
        user = userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
