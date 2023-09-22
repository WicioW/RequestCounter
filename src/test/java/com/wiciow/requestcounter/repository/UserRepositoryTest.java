package com.wiciow.requestcounter.repository;

import com.wiciow.requestcounter.MongoTestContainerBase;
import com.wiciow.requestcounter.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest extends MongoTestContainerBase {

  @Autowired
  private UserRepository testObj;

  @BeforeEach
  void setUp(){
    testObj.deleteAll();
  }

  @Test
  void testFindByLogin(){
    //given
    String login = "Wn5u4";
    User user = User.builder()
        .login(login)
        .requestCount(1)
        .build();
    testObj.save(user);
    //when
    Optional<User> result = testObj.findByLogin(login);
    //then
    assertThat(result).isPresent();
    assertThat(result.get()).usingRecursiveComparison().isEqualTo(user);
  }
}