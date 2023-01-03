package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.model.enumeration.Role;
import com.example.tsipadan.petproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @DisplayName("Should Find User By username")
    void shouldFindUserByUsername() {
//        UserServiceImpl userService = new UserServiceImpl(userMapper, userRepository, passwordEncoder);
//        User user = new User(123L, "user1", "$2a$10$T9hJqOJ9Lz.S5dmFEqshM.NQxjR1uehV2NqNrccXQ3rfyQq1/6462",
//                "email@mail.com", "Franck", "Ocean", "Franciscofich",
//                new Date(1997-5-25), Role.USER, null, null);
//        UserDTO userDTO = new UserDTO(123L, "user1", "$2a$10$T9hJqOJ9Lz.S5dmFEqshM.NQxjR1uehV2NqNrccXQ3rfyQq1/6462",
//                "email@mail.com", Role.USER, "Franck", "Ocean", "Franciscofich",
//                new Date(1997-5-25), null, null);
//        Mockito.when(userRepository.findByUsername("user1")).thenReturn(user);
//        Mockito.when(userMapper.mapToDTO(Mockito.any(User.class))).thenReturn(userDTO);
//
//        UserDTO actualResponse = userService.findByUsername("user1");
//
//        Assertions.assertEquals(actualResponse.getId(), userDTO.getId());
//        Assertions.assertEquals(actualResponse.getUsername(), userDTO.getUsername());


        //TODO сделать так чтобы юзер и прочая фигня собиралась до тестов

    }

    @Test
    @DisplayName("Should Save User")
    public void shouldSaveUser() {
//        UserServiceImpl userService = new UserServiceImpl(userMapper, userRepository, passwordEncoder);
//        User user = new User(125L, "user2", "$2a$10$T9hJqOJ9Lz.S5dmFEqshM.NQxjR1uehV2NqNrccXQ3rfyQq1/6462",
//                "email@mail.com", "Franck", "Ocean", "Franciscofich",
//                new Date(1997-5-25), Role.USER, null, null);
//        User user = new UserDTO(123L, "user2", "$2a$10$T9hJqOJ9Lz.S5dmFEqshM.NQxjR1uehV2NqNrccXQ3rfyQq1/6462",
//                "email@mail.com", Role.USER, "Franck", "Ocean", "Franciscofich",
//                new Date(1997-5-25), null, null);
//
////        Mockito.when(userRepository.findByUsername("user2")).thenReturn(user);
//        Mockito.when(userMapper.mapToDTO(user)).thenReturn(userDTO);
////        Mockito.when(userMapper.mapToEntity(userDTO)).thenReturn(user);
//
//        userService.createUser(user);
//
//        Mockito.verify(userRepository, Mockito.times(1))
//                .save(ArgumentMatchers.any(User.class));
//
//        Assertions.assertEquals(userArgumentCaptor.getValue().getId(), 123L);
//        Assertions.assertEquals(userArgumentCaptor.getValue().getUsername(), "user2");

    }

}