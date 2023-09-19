package com.wiciow.requestcounter.github;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClientException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GithubApiServiceTest {

  @Autowired
  private GithubApiService testObj;

  @RegisterExtension
  static WireMockExtension wireMockServer = WireMockExtension.newInstance()
      .options(wireMockConfig().dynamicPort())
      .build();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("github.api.url", wireMockServer::baseUrl);
  }

  @Test
  void shouldGetGithubUser() {
    String login = "octocat";
    wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody("""
                {
                    "login": "%s",
                    "id": 1321,
                    "name": "The Octocat",
                    "avatar_url": "https://avatars.githubusercontent.com/u/29005429?v=4",
                    "public_repos": 9,
                    "created_at": "2017-05-27T19:37:34Z",
                    "followers": 1,
                    "type": "User"
                }
                """
                .formatted(login)
            )));

    GithubUserResponseDTO result = testObj.getUser(login);

    assertThat(result.id()).isEqualTo(1321L);
    assertThat(result.login()).isEqualTo(login);
    assertThat(result.name()).isEqualTo("The Octocat");
    assertThat(result.avatarUrl()).isEqualTo(
        "https://avatars.githubusercontent.com/u/29005429?v=4");
    assertThat(result.publicRepos()).isEqualTo(9);
    assertThat(result.createdAt()).isEqualTo("2017-05-27T19:37:34Z");
    assertThat(result.followers()).isEqualTo(1);
    assertThat(result.type()).isEqualTo("User");
  }

  @Test
  void shouldThrowExceptionWithTheSameMessageAsFromApi_whenApiRespondsWithError() {
    String login = "octocat";
    wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
        .willReturn(WireMock.aResponse()
            .withStatus(500)
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody("""
                {
                    "message": "API error - contact support"
                }
                """
            )));

    RestClientException result = assertThrows(RestClientException.class,
        () -> testObj.getUser(login));

    assertThat(result.getMessage()).contains("500 Server Error");
    assertThat(result.getMessage()).contains("API error - contact support");
  }


}