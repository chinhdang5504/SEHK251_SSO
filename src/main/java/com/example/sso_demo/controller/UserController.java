package com.example.sso_demo.controller;

import com.example.sso_demo.dto.UserUpdateRequest;
import com.example.sso_demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @GetMapping("/users/my-info")
    ResponseEntity<?> getMyInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/users/my-info/{username}")
    ResponseEntity<?> getMyInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getMyInfo(username));
    }

    @PutMapping("/users/{userId}")
    ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
