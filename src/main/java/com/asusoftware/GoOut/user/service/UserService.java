package com.asusoftware.GoOut.user.service;

import com.asusoftware.GoOut.user.model.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto user);
    Optional<UserDto> getUserById(UUID id);
    List<UserDto> getAllUsers();
    void deleteUser(UUID id);
    UserDto updateUser(UUID id, UserDto updatedUser);
    List<UserDto> searchUsers(String keyword);
}
