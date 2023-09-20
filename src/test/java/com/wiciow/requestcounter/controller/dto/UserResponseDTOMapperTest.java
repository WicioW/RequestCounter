package com.wiciow.requestcounter.controller.dto;

import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserResponseDTOMapperTest {

  private final UserResponseDTOMapper testObj = new UserResponseDTOMapper();

  private final EasyRandom easyRandom = new EasyRandom();

  @Test
  void shouldReturnObjectWithFilledFields_whenProvidedFieldsAreNotNull(){
    //given
    GithubUserResponseDTO user = GithubUserResponseDTO.builder()
        .id(easyRandom.nextLong())
        .login(easyRandom.nextObject(String.class))
        .name(easyRandom.nextObject(String.class))
        .type(easyRandom.nextObject(String.class))
        .avatarUrl(easyRandom.nextObject(String.class))
        .createdAt(easyRandom.nextObject(String.class))
        .build();

    BigDecimal userCalculations = easyRandom.nextObject(BigDecimal.class);
    //when
    UserResponseDTO result = testObj.mapToResponse(user, userCalculations);
    //then
    assertThat(result.id()).isEqualTo(user.id());
    assertThat(result.login()).isEqualTo(user.login());
    assertThat(result.name()).isEqualTo(user.name());
    assertThat(result.type()).isEqualTo(user.type());
    assertThat(result.avatarUrl()).isEqualTo(user.avatarUrl());
    assertThat(result.createdAt()).isEqualTo(user.createdAt());
    assertThat(result.calculations()).isEqualTo(userCalculations);
  }

  @Test
  void shouldReturnObjectWithNullFields_whenProvidedFieldsAreNull(){
    //given
    GithubUserResponseDTO user = GithubUserResponseDTO.builder().build();
    //when
    UserResponseDTO result = testObj.mapToResponse(user, null);
    //then
    assertThat(result.id()).isNull();
    assertThat(result.login()).isNull();
    assertThat(result.name()).isNull();
    assertThat(result.type()).isNull();
    assertThat(result.avatarUrl()).isNull();
    assertThat(result.createdAt()).isNull();
    assertThat(result.calculations()).isNull();
  }


}