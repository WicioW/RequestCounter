package com.wiciow.requestcounter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GithubRestClientConfig {

  @Value("${github.api.url}")
  private String githubApiUrl;

  @Bean
  RestClient restClient(RestClient.Builder builder) {
    return builder.baseUrl(githubApiUrl)
        .build();
  }
}
