package com.wiciow.requestcounter.github;

import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GithubApiService {

    private final RestClient restClient;

    public GithubUserResponseDTO getUser(String login) {
        return restClient
                .get()
                .uri("/users/{login}", login)
                .retrieve()
                .body(GithubUserResponseDTO.class);
    }
}
