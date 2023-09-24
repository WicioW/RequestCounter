package com.wiciow.requestcounter.github;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.wiciow.requestcounter.exception.GithubApiException;
import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

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
    //given
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
    //when
    GithubUserResponseDTO result = testObj.getUser(login);
    //then
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

  @ParameterizedTest
  @ValueSource(ints = {400, 401, 403, 404, 422, 500, 502, 503, 504})
  void shouldThrowGitHubApiExceptionWithTheSameMessageAsFromApi_whenApiRespondsWithException(int statusCode) {
    //given
    String login = "octocat";
    wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/users/" + login))
        .willReturn(WireMock.aResponse()
            .withStatus(statusCode)
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody("""
                {
                    "message": "API error - contact support"
                }
                """
            )));

    //when-then
    GithubApiException result = assertThrows(GithubApiException.class,
        () -> testObj.getUser(login));

    assertThat(result.getMessage()).contains("API error - contact support");
  }


}