package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    private List<UserDTO> getAllUsers() {
        return userService.showAllUsers();
    }

    @GetMapping("/{id}")
    private UserDTO getUserById(@PathVariable UUID id) {
        return userService.findUserById(id);
    }

    @PostMapping
    private UserDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    private UserDTO updateUser(@PathVariable UUID id,
                               @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @PutMapping("/{id}/change")
    private String updatePassword(@PathVariable UUID id,
                                  @RequestBody List<String> listOfPass) {
        return userService.updatePassword(id, listOfPass);
    }

    @DeleteMapping("/{id}")
    private String deleteUserById(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    /*
     * JWT TOKEN
     *  https://devwithus.com/spring-boot-rest-api-security-jwt/
     *
     * //настроить авторизацию на методы, чтобы не каждый мог делать определенные операции
     *
     */

}
