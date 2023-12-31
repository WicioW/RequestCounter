package com.wiciow.requestcounter.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wiciow.requestcounter.controller.dto.UserResponseDTO;
import com.wiciow.requestcounter.service.UserService;
import java.math.BigDecimal;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private final EasyRandom easyRandom = new EasyRandom();

  @Test
  void shouldReturnUser_whenLoginIsProvided() throws Exception {
    //given
    long id = easyRandom.nextLong();
    String login = easyRandom.nextObject(String.class);
    String name = easyRandom.nextObject(String.class);
    String type = easyRandom.nextObject(String.class);
    String avatarUrl = easyRandom.nextObject(String.class);
    String createdAt = easyRandom.nextObject(String.class);
    BigDecimal calculations = easyRandom.nextObject(BigDecimal.class);

    UserResponseDTO userResponseDTO = UserResponseDTO.builder()
        .id(id)
        .login(login)
        .name(name)
        .type(type)
        .avatarUrl(avatarUrl)
        .createdAt(createdAt)
        .calculations(calculations)
        .build();
    //when-then
    when(userService.getUser(login)).thenReturn(userResponseDTO);
    mockMvc.perform(get("/users/{login}", login)
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.login").value(login))
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.type").value(type))
        .andExpect(jsonPath("$.avatarUrl").value(avatarUrl))
        .andExpect(jsonPath("$.createdAt").value(createdAt))
        .andExpect(jsonPath("$.calculations").value(calculations));
  }

  @Test
  void shouldReturn404_whenUserLoginIsMissing() throws Exception {
    //given
    long id = easyRandom.nextLong();
    String login = easyRandom.nextObject(String.class);
    String name = easyRandom.nextObject(String.class);
    String type = easyRandom.nextObject(String.class);
    String avatarUrl = easyRandom.nextObject(String.class);
    String createdAt = easyRandom.nextObject(String.class);
    BigDecimal calculations = easyRandom.nextObject(BigDecimal.class);

    UserResponseDTO userResponseDTO = UserResponseDTO.builder()
        .id(id)
        .login(login)
        .name(name)
        .type(type)
        .avatarUrl(avatarUrl)
        .createdAt(createdAt)
        .calculations(calculations)
        .build();
    //when-then
    when(userService.getUser(login)).thenReturn(userResponseDTO);
    mockMvc.perform(get("/users/")
            .contentType("application/json"))
        .andExpect(status().isNotFound());
  }
}