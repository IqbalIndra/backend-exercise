package com.example._9.user_service.controller;

import com.example._9.user_service.dto.request.UserRequest;
import com.example._9.user_service.dto.response.UserResponse;
import com.example._9.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findUsers(@RequestParam(value = "page_num", defaultValue = "0") Integer pageNum,
                                      @RequestParam(value = "page_size", defaultValue = "5") Integer pageSize){
        List<UserResponse> users = userService.findAll(pageNum,pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("users",users);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUsers(@PathVariable("id") Integer id){

        UserResponse user = userService.findById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("user",user);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestParam(name = "name") String name){

        UserResponse user = userService.addUser(name);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("user",user);

        return ResponseEntity.ok(result);
    }
}
