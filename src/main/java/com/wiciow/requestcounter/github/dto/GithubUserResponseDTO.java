package com.wiciow.requestcounter.github.dto;

public record GithubUserResponseDTO(
        Long id,
        String login,
        String name,
        String type,
        String avatarUrl,
        String createdAt
) {
}
