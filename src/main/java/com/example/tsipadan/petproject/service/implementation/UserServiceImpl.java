package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.exception.EntityDuplicateException;
import com.example.tsipadan.petproject.exception.EntityNotFoundException;
import com.example.tsipadan.petproject.exception.IncorrectValueException;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.model.enumeration.Role;
import com.example.tsipadan.petproject.repository.UserRepository;
import com.example.tsipadan.petproject.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
//https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction-declarative-annotations
public class UserServiceImpl implements UserService {

    private static final String CHANGED_PASSWORD = "Password has been changed";
    private static final String DELETE = "User has been deleted";

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserById(UUID id) {
        log.info("Find user with < {} >  ", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userMapper.mapToDTO(userOptional.get());
        } else
            throw new EntityNotFoundException("User with " + id + " not found");
    }

    @Override
    public List<UserDTO> showAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDTO createUser(User entity) {
        Optional<User> find = userRepository.findByUsername(entity.getUsername());
        if (find.isPresent()) {
            if (find.get().getUsername().equals(entity.getUsername())) {
                throw new EntityDuplicateException("User with username < " + entity.getUsername() + " > already exist");
            }
            if (find.get().getEmail().equals(entity.getEmail())) {
                throw new EntityDuplicateException("User with email < " + entity.getEmail() + " > already exist");
            }
        }
        User result = userRepository.save(setUserFieldsForCreate(entity));
        log.info("Create user < {} >", result.getId());
        return userMapper.mapToDTO(result);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserOrThrowException(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else
            throw new EntityNotFoundException("User with id < " + userId + " > doesn't exist");
    }

    @Transactional
    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        log.info("Update user with id < {} >", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent() == Boolean.FALSE) {
            throw new EntityNotFoundException("User < " + id + " > doesn't exist");
        } else {
            User user = userOptional.get();
            User result = userRepository.save(setUserFieldsForUpdate(user, userDTO));
            return userMapper.mapToDTO(result);
        }
    }

    @Transactional
    @Override
    public String updatePassword(UUID id, List<String> listOfPass) {
        Optional<User> find = userRepository.findById(id);
        if (find.isPresent()) {
            User user = find.get();
            if (passwordEncoder.matches(listOfPass.get(0), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(listOfPass.get(1)));
                userRepository.save(user);
                log.info("Successful changed password < {} >", id);
                return CHANGED_PASSWORD;
            } else
                throw new IncorrectValueException("Old password doesn't match with existing user`s password");
        } else
            throw new EntityNotFoundException("User with " + id + " id, not found");
    }

    @Transactional
    @Override
    public String deleteUser(UUID id) {
        Optional<User> find = userRepository.findById(id);
        if (find.isPresent()) {
            userRepository.deleteById(id);
            log.info("Successful find and delete user");
            return DELETE;
        } else
            throw new EntityNotFoundException("User with id < " + id + " > doesn't exist");
    }

    private User setUserFieldsForCreate(User entity) {
        User user = new User();
        user.setUsername(entity.getUsername());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setMiddleName(entity.getMiddleName());
        user.setBirthday(entity.getBirthday());
        user.setRole(Role.USER);
        return user;
    }

    private User setUserFieldsForUpdate(User user, UserDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setEmail(userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
        return user;
    }

}
