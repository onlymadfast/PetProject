package com.example.tsipadan.petproject.controller;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.model.Order;
import com.example.tsipadan.petproject.model.Response;
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

    @GetMapping("/orders/{userId}")
    private List<OrderDTO> getAllOrdersByUsername(@PathVariable UUID userId) {
        return orderService.getAllOrdersByUsername(userId);
    }

    @PostMapping("/make/{userId}")
    private UserDTO createOrder(@PathVariable UUID userId,
                                @RequestBody Order entity) {
        return orderService.createOrder(userId, entity);
    }

    @DeleteMapping("/edit/{orderId}/{userId}")
    private Response deleteOrder(@PathVariable UUID orderId,
                                 @PathVariable UUID userId) {
        return orderService.deleteOrderByOrderId(orderId, userId);
    }

}
