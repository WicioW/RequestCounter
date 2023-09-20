package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.github.dto.GithubUserResponseDTO;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class UserCalculationsService {

  public BigDecimal getUserCalculations(@NonNull GithubUserResponseDTO user) {
    if(user.followers() == 0) {
      return toBigDecimal(0);
    }
    BigDecimal firstPart = BigDecimal.valueOf(6)
        .divide(BigDecimal.valueOf(user.followers()), 2, RoundingMode.HALF_UP);
    BigDecimal secondPart = BigDecimal.valueOf(2)
        .add(BigDecimal.valueOf(user.publicRepos()));
    return firstPart.multiply(secondPart);
  }

  private BigDecimal toBigDecimal(Integer integer){
    return BigDecimal.valueOf(integer).setScale(2, RoundingMode.HALF_UP);
  }

}
