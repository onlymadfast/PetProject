package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.Response;
import com.example.tsipadan.petproject.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * Find user by id
     *
     * @param uuid - user identifier
     * @return - user dto
     */
    UserDTO findUserById(UUID uuid);

    /**
     * Create user
     *
     * @param userDTO - user model
     * @return - user dto
     */
    UserDTO createUser(User userDTO);

    //обычное сохранение клиента, выделено чтобы не иметь репозиторий в ином сервисе
    User saveUser(User user);

    //выделенный отдельный метод для поиска с возвратом оригинальной модели
    User findUserOrThrowException(UUID userId);

    /**
     * Edit user by id
     *
     * @param userId  - user identifier
     * @param userDTO - user model
     * @return - user dto
     */
    UserDTO updateUser(UUID userId, UserDTO userDTO);

    /**
     * Get all users
     *
     * @return - list of users
     */
    List<UserDTO> showAllUsers();

    /**
     * Update old password by comparing old with new pass
     *
     * @param userId     - user who wont to change
     * @param listOfPass - old pass and new pass in object
     * @return - response of operation
     */
    Response updatePassword(UUID userId, List<String> listOfPass);

    /**
     * Delete user by id
     *
     * @param userId - user identifier
     * @return - response of operation
     */
    Response deleteUser(UUID userId);

}
