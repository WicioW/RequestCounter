package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.controller.dto.UserResponseDTO;
import com.wiciow.requestcounter.controller.dto.UserResponseDTOMapper;
import com.wiciow.requestcounter.github.GithubApiService;
import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

  private final GithubApiService githubApiService;
  private final UserCalculationsService userCalculationsService;
  private final UserResponseDTOMapper userResponseDTOMapper;
  private final UserCreator userCreator;
  private final UserUpdater userUpdater;

  public UserResponseDTO getUser(@NonNull String login) {
    GithubUserResponseDTO user = githubApiService.getUser(login);
    incrementCounterForUserWithLogin(login);
    BigDecimal userCalculations = userCalculationsService.getUserCalculations(user);
    return userResponseDTOMapper.mapToResponse(user, userCalculations);
  }

  private void incrementCounterForUserWithLogin(String login) {
    userCreator.createIfNotFoundByLogin(login);
    userUpdater.incrementCounterByLogin(login);
  }

}
