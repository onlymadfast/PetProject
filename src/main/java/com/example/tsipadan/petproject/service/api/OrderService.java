package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.Address;
import com.example.tsipadan.petproject.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    //получить все заказы пользователя
    List<OrderDTO> getAllOrdersByUsername(UUID userId);

    //метод создания заказа для клиента
    UserDTO createOrder(UUID userId, Order entity);

    //оплата заказа
    boolean payOrder(UUID userId, UUID orderId);

    //проверка заказа и конечное подтверждение
    boolean checkOrderAndCommit(UUID userId, UUID orderId);

    //смена approved статуса
    OrderDTO changeApprovedFlagToTrue(UUID orderId);

    //удаление заказа по идентификатору у конкретного клиента
    String deleteOrderByOrderId(UUID orderId, UUID userId);

}
