package com.asusoftware.GoOut.user.service.impl;

import com.asusoftware.GoOut.user.model.User;
import com.asusoftware.GoOut.user.model.dto.UserDto;
import com.asusoftware.GoOut.user.repository.UserRepository;
import com.asusoftware.GoOut.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User user = toEntity(dto);
        user.setCreatedAt(LocalDateTime.now());
        return toDto(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(UUID id, UserDto dto) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setBio(dto.getBio());
                    existing.setProfilePic(dto.getProfilePic());
                    existing.setInterests(dto.getInterests());
                    return toDto(userRepository.save(existing));
                })
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public List<UserDto> searchUsers(String keyword) {
        return userRepository.findAll().stream()
                .filter(u -> u.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        u.getInterests().stream().anyMatch(i -> i.toLowerCase().contains(keyword.toLowerCase())))
                .map(this::toDto)
                .toList();
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setBio(dto.getBio());
        user.setProfilePic(dto.getProfilePic());
        user.setInterests(dto.getInterests());
        user.setRole(dto.getRole());
        return user;
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePic(user.getProfilePic());
        dto.setInterests(user.getInterests());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRole(user.getRole());
        return dto;
    }
}

