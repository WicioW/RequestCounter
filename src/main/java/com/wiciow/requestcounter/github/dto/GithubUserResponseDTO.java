package com.wiciow.requestcounter.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubUserResponseDTO(
        Long id,
        String login,
        String name,
        String type,
        @JsonProperty("avatar_url")
        String avatarUrl,
        @JsonProperty("created_at")
        String createdAt,
        Integer followers,
        @JsonProperty("public_repos")
        Integer publicRepos
) {
}
