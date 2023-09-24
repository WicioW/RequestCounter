package com.wiciow.requestcounter.repository;

import com.wiciow.requestcounter.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByLogin(String login);
}
