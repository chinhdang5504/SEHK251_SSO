package com.example.sso_demo.mapper;

import com.example.sso_demo.dto.UserCreationRequest;
import com.example.sso_demo.dto.UserResponse;
import com.example.sso_demo.dto.UserUpdateRequest;
import com.example.sso_demo.entity.Role;
import com.example.sso_demo.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    // Custom mapping cho roles
    default Set<Role> mapRoles(List<String> roles) {
        if (roles == null) return null;
        return roles.stream().map(r -> new Role(r, null)).collect(Collectors.toSet());
    }
}
