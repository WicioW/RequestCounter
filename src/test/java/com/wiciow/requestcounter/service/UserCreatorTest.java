package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.model.User;
import com.wiciow.requestcounter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;

@MockitoSettings
class UserCreatorTest {

  @InjectMocks
  private UserCreator testObj;

  @Mock
  private UserRepository userRepository;

  @Test
  void createIfNotFoundLogin_shouldReturnUserFromDB_whenUserExistsInDB() {
    // given
    String login = "login";
    User userResponse = User.builder()
        .login(login)
        .build();
    when(userRepository.findByLogin(login)).thenReturn(Optional.of(userResponse));
    // when
    var result = testObj.createIfNotFoundByLogin(login);
    // then
    assertThat(result).isEqualTo(userResponse);
  }

  @Test
  void createIfNotFoundLogin_shouldReturnNewUser_whenUserNotFoundInDB(){
    // given
    String login = "login";
    User userResponse = User.builder()
        .login(login)
        .build();
    when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
    when(userRepository.save(refEq(userResponse))).thenReturn(userResponse);
    // when
    var result = testObj.createIfNotFoundByLogin(login);
    // then
    assertThat(result).isEqualTo(userResponse);
  }

}