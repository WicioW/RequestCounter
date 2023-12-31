package com.wiciow.requestcounter.service;

import com.wiciow.requestcounter.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUpdater {

  private final MongoTemplate mongoTemplate;

  public void incrementCounterByLogin(String login) {
    mongoTemplate.updateFirst(
        Query.query(Criteria.where("login")
            .is(login)),
        new Update().inc("requestCount", 1),
        User.class
    );
    log.info("Incremented request count for user with login: {}", login);
  }
}
