package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.repository.UserRepository;
import com.example.tsipadan.petproject.service.api.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Should Get All Users")
    public void getAllUsers() {

    }



}
