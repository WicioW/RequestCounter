package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.model.User;
import com.wiciow.requestcounter.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCreator {

  private final UserRepository userRepository;

  public User createIfNotFoundByLogin(String login) {
    Optional<User> optionalUser = userRepository.findByLogin(login);
    if (optionalUser.isEmpty()) {
      User user = User.builder()
          .login(login)
          .build();
      user = userRepository.save(user);
      log.info("Created user in db: {}", user.toLogString());
      return user;
    }
    return optionalUser.get();
  }
}
