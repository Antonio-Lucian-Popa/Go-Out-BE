package com.asusoftware.GoOut.user.model.dto;

import com.asusoftware.GoOut.user.model.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String bio;
    private List<String> interests;
    private String profilePic;
    private LocalDateTime createdAt;
    private UserRole role;
}
