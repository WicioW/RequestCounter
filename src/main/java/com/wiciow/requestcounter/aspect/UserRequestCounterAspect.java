package com.wiciow.requestcounter.aspect;

import com.wiciow.requestcounter.service.UserCreator;
import com.wiciow.requestcounter.service.UserUpdater;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserRequestCounterAspect {

  private final UserCreator userCreator;
  private final UserUpdater userUpdater;

  @After("@annotation(com.wiciow.requestcounter.annotation.UpdateUserRequestCounter)")
  public void updateUserRequestCounter(){
    String login = "login";
    userCreator.createIfNotFoundByLogin(login);
    userUpdater.incrementCounterByLogin(login);
  }

}
