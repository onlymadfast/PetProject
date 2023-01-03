package com.example.tsipadan.petproject.mapper;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserMapper {

    private final ModelMapper mapper;

    //convert Entity to DTO
    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    //convert DTO to Entity
    public User mapToEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
