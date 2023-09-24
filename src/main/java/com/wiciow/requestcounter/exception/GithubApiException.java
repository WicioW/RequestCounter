package com.wiciow.requestcounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GithubApiException extends RuntimeException {

  public GithubApiException(String message) {
    super(message);
  }
}
