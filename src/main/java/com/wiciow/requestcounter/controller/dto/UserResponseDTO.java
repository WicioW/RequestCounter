package com.wiciow.requestcounter.controller.dto;

import java.math.BigDecimal;
import lombok.Builder;

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
