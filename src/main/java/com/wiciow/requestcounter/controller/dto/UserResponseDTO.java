package com.wiciow.requestcounter.controller.dto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record UserResponseDTO(
        Long id,
        String login,
        String name,
        String type,
        String avatarUrl,
        String createdAt,
        BigDecimal calculations
) {
}
