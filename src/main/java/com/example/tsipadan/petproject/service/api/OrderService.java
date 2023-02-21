package com.example.tsipadan.petproject.service.api;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.Order;
import com.example.tsipadan.petproject.model.Response;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    /**
     * Get all orders from user
     *
     * @param userId - user identifier
     * @return - list of orders
     */
    List<OrderDTO> getAllOrdersByUsername(UUID userId);

    /**
     * Create order by user
     * <p>
     * расписать конкретные этапы создания заказа
     *
     * @param userId - user who want buy something
     * @param entity - order object
     * @return - user with orders
     */
    UserDTO createOrder(UUID userId, Order entity);

    //оплата заказа
    boolean payOrder(UUID userId, UUID orderId);

    //проверка заказа и конечное подтверждение
    boolean checkOrderAndCommit(UUID userId, UUID orderId);

    //смена approved статуса
    OrderDTO changeApprovedFlagToTrue(UUID orderId);

    /**
     * Delete order from user by user identifier
     *
     * @param orderId - order identifier
     * @param userId  - user identifier
     * @return - response of operation
     */
    Response deleteOrderByOrderId(UUID orderId, UUID userId);

}
