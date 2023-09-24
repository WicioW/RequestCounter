package com.wiciow.requestcounter.controller.dto;

import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import java.math.BigDecimal;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserResponseDTOMapper {

  public UserResponseDTO mapToResponse(@NonNull GithubUserResponseDTO user,
                                       BigDecimal userCalculations) {
    return UserResponseDTO.builder()
        .id(user.id())
        .login(user.login())
        .name(user.name())
        .type(user.type())
        .avatarUrl(user.avatarUrl())
        .createdAt(user.createdAt())
        .calculations(userCalculations)
        .build();
  }
}
