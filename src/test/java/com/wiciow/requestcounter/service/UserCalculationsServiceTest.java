package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UserCalculationsServiceTest {

  @ParameterizedTest
  @MethodSource("getUserCalculations")
  void testIfCalculationsAreCorrect(Integer followers, Integer publicRepos, BigDecimal expected) {
    //given
    UserCalculationsService userCalculationsService = new UserCalculationsService();
    GithubUserResponseDTO user = GithubUserResponseDTO.builder()
        .followers(followers)
        .publicRepos(publicRepos)
        .build();

    //when
    BigDecimal result = userCalculationsService.getUserCalculations(user);
    //then
    assertThat(result).isEqualTo(expected);
  }

  private static Stream<Arguments> getUserCalculations(){
    return Stream.of(
        arguments(1, 1, BigDecimal.valueOf(18).setScale(2, RoundingMode.HALF_UP)),
        arguments(1, 0, BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP)),
        arguments(6, 0, BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP)),
        arguments(12, 0, BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP)),
        arguments(9,0, BigDecimal.valueOf(1.34).setScale(2, RoundingMode.HALF_UP)),
        arguments(10472, 8, BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP)),
        arguments(0, 999, BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP))
    );
  }

}