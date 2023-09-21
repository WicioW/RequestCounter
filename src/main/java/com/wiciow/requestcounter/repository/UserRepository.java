package com.wiciow.requestcounter.repository;

import com.wiciow.requestcounter.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByLogin(String login);
}
