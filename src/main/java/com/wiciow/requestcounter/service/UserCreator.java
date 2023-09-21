package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.model.User;
import com.wiciow.requestcounter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreator {

  private final UserRepository userRepository;

  public User createIfNotFoundByLogin(String login) {
    Optional<User> optionalUser = userRepository.findByLogin(login);
    if (optionalUser.isEmpty()) {
      User user = User.builder()
          .login(login)
          .build();
      user = userRepository.save(user);
      return user;
    }
    return optionalUser.get();
  }
}
