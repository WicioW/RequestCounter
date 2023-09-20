package com.wiciow.requestcounter.controller;

import com.wiciow.requestcounter.controller.dto.UserResponseDTO;
import com.wiciow.requestcounter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

    @GetMapping("/{login}")
    public UserResponseDTO getUser(@PathVariable String login) {
        return userService.getUser(login);
    }
}
