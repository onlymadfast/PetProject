package com.example.tsipadan.petproject.dto;

import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.model.enumeration.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        UserDTO userDTO = userMapper.mapToDTO(new User(123L, "user1", "$2a$10$T9hJqOJ9Lz.S5dmFEqshM.NQxjR1uehV2NqNrccXQ3rfyQq1/6462",
                "email@mail.com", "Franck", "Ocean", "Franciscofich",
                new Date(1997-5-25), Role.USER, null, null));
        Assertions.assertEquals(123L, userDTO.getId());
        Assertions.assertEquals("user1", userDTO.getUsername());
        Assertions.assertEquals("email@mail.com", userDTO.getEmail());
        Assertions.assertEquals("Franck", userDTO.getFirstName());
        Assertions.assertEquals("Ocean", userDTO.getLastName());
        Assertions.assertEquals("Franciscofich", userDTO.getMiddleName());
    }

}
