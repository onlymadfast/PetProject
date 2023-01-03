package com.example.tsipadan.petproject.mapper;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class OrderMapper {

    private final ModelMapper mapper;

    //convert Entity to DTO
    public OrderDTO mapToDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }

    //convert DTO to Entity
    public Order mapToEntity(OrderDTO orderDTO) {
        return mapper.map(orderDTO, Order.class);
    }
}
