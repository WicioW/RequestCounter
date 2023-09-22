package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.controller.dto.UserResponseDTO;
import com.wiciow.requestcounter.controller.dto.UserResponseDTOMapper;
import com.wiciow.requestcounter.github.GithubApiService;
import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class UserServiceTest {

  @InjectMocks
  private UserService testObj;

  @Mock
  private GithubApiService githubApiService;
  @Mock
  private UserCalculationsService userCalculationsService;
  @Mock
  private UserResponseDTOMapper userResponseDTOMapper;
  @Mock
  private UserCreator userCreator;
  @Mock
  private UserUpdater userUpdater;

  @Test
  void testGetUser() {
    // given
    String login = "login";
    GithubUserResponseDTO user = GithubUserResponseDTO.builder()
        .build();
    BigDecimal userCalculations = BigDecimal.ZERO;
    UserResponseDTO userResponse = UserResponseDTO.builder()
        .build();

    when(githubApiService.getUser(login)).thenReturn(user);
    when(userCalculationsService.getUserCalculations(user)).thenReturn(userCalculations);
    when(userResponseDTOMapper.mapToResponse(user, userCalculations)).thenReturn(userResponse);
    // when
    var result = testObj.getUser(login);
    // then
    assertThat(result).isEqualTo(userResponse);
    verify(userCreator).createIfNotFoundByLogin(login);
    verify(userUpdater).incrementCounterByLogin(login);
  }

}