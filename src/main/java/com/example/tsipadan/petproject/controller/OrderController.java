package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.Order;
import com.example.tsipadan.petproject.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userId}/orders")
    private List<OrderDTO> getAllOrdersByUsername(@PathVariable UUID userId) {
        return orderService.getAllOrdersByUsername(userId);
    }

    @PostMapping("/{userId}")
    private UserDTO createOrder(@PathVariable UUID userId,
                                @RequestBody Order entity) {
        return orderService.createOrder(userId, entity);
    }

    @DeleteMapping("/{orderId}/{userId}")
    private String deleteOrder(@PathVariable UUID orderId,
                               @PathVariable UUID userId) {
        return orderService.deleteOrderByOrderId(orderId, userId);
    }

}
