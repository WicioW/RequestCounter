package com.wiciow.requestcounter.service;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

import com.wiciow.requestcounter.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.mongodb.core.MongoTemplate;

@MockitoSettings
class UserUpdaterTest {

  @InjectMocks
  private UserUpdater testObj;

  @Mock
  private MongoTemplate mongoTemplate;

  @Test
  void incrementCounterByLogin() {
    // given
    String login = "login";
    // when
    testObj.incrementCounterByLogin(login);
    // then
    verify(mongoTemplate).updateFirst(
        assertArg(query -> query.getQueryObject()
            .get("login")
            .equals(login)),
        assertArg(update -> update.getUpdateObject()
            .get("$inc")
            .equals("{ \"requestCount\" : 1}")),
        (Class<?>) assertArg(User.class::equals)
    );
  }
}