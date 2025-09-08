package com.example.sso_demo.service;

import com.example.sso_demo.dto.UserResponse;
import com.example.sso_demo.dto.UserUpdateRequest;
import com.example.sso_demo.entity.User;
import com.example.sso_demo.exception.ServerException;
import com.example.sso_demo.mapper.UserMapper;
import com.example.sso_demo.repository.RoleRepository;
import com.example.sso_demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo(String name) {

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
