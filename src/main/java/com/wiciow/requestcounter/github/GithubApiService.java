package com.wiciow.requestcounter.github;

import com.wiciow.requestcounter.exception.GithubApiException;
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
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            mapErrorMessage())
        .body(GithubUserResponseDTO.class);
  }

  private RestClient.ResponseSpec.ErrorHandler mapErrorMessage() {
    return (request, response) -> {
      throw new GithubApiException("Github API error - " + new String(response.getBody()
          .readAllBytes()));
    };
  }

}
