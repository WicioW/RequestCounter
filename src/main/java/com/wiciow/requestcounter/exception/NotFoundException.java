package com.wiciow.requestcounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

  public NotFoundException(Class clazz, Object id) {
    super(String.format("%s[id:%s] not found.", clazz.getSimpleName(), id));
  }
}
