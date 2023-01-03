package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    //поиск клиента по имени, ограничить такой поиск по правам?
    UserDTO findUserById(UUID uuid);

    //создание клиента
    UserDTO createUser(User userDTO);

    //обычное сохранение клиента, выделено чтобы не иметь репозиторий в ином сервисе
    User saveUser(User user);

    //выделенный отдельный метод для поиска с возвратом оригинальной модели
    User findUserOrThrowException(UUID userId);

    //обновление клиента в личном кабинете
    UserDTO updateUser(UUID id, UserDTO userDTO);

    //показ всех клиентов, ограничить использование этим методом
    List<UserDTO> showAllUsers();

    //обновление пароля при совпадении со старым
    String updatePassword(UUID id, List<String> listOfPass);

    //удаление клиента, ограничить доступ по правам
    String deleteUser(UUID id);

}
