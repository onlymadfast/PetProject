package com.example.tsipadan.petproject.service.implementation;

import com.example.tsipadan.petproject.dto.OrderDTO;
import com.example.tsipadan.petproject.dto.UserDTO;
import com.example.tsipadan.petproject.exception.EntityNotFoundException;
import com.example.tsipadan.petproject.exception.IncorrectValueException;
import com.example.tsipadan.petproject.mapper.OrderMapper;
import com.example.tsipadan.petproject.mapper.UserMapper;
import com.example.tsipadan.petproject.model.Address;
import com.example.tsipadan.petproject.model.Item;
import com.example.tsipadan.petproject.model.Order;
import com.example.tsipadan.petproject.model.User;
import com.example.tsipadan.petproject.model.enumeration.StatusOrder;
import com.example.tsipadan.petproject.model.enumeration.StatusPay;
import com.example.tsipadan.petproject.repository.OrderRepository;
import com.example.tsipadan.petproject.service.api.AddressService;
import com.example.tsipadan.petproject.service.api.ItemService;
import com.example.tsipadan.petproject.service.api.OrderService;
import com.example.tsipadan.petproject.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String DELETE = "Order has been deleted";

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final AddressService addressService;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> getAllOrdersByUsername(UUID userId) {
        return orderRepository.findOrderByUserId(userId).stream()
                .map(orderMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDTO createOrder(UUID userId, Order entity) {
        //ищем клиента по идентификатору или кидаем исключение
        User user = userService.findUserOrThrowException(userId);
        //устанавливаем заказу стандартное значение статуса оплаты
        entity.setStatusPay(StatusPay.NOT_PAID);
        entity.setApprovedFlag(Boolean.FALSE);
        //ожидает оплаты только если у клиента есть адрес
        if (user.getAddress() !=null) {
            entity.setStatusOrder(StatusOrder.PENDING_PAYMENT);
        } else {
            entity.setStatusOrder(StatusOrder.WITHOUT_ADDRESS);
        }
        //пихаем к существующим заказам клиента и сохраняем текущий заказ
        entity.setUser(user);
        //добавляем новый заказ к существующим заказам клиента
        user.getOrder().add(entity);
        //сохраняем объект заказа
        Order savedOrder = orderRepository.save(entity);
        //обновляем клиента, так как добавили заказ
        User saveUser = userService.saveUser(user);
        log.info("Create order < {} > for user < {} >", savedOrder.getId(), saveUser.getId());
        return userMapper.mapToDTO(saveUser);
    }

    @Transactional
    @Override
    public boolean payOrder(UUID userId, UUID orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatusPay(StatusPay.PAID);
            order.setStatusOrder(StatusOrder.ON_THE_WAY);
            Order save = orderRepository.save(order);
            log.info("Order paid < {} >", save.getId());
            return true;
        } else
            throw new EntityNotFoundException("Order with id < " + orderId + " > doesn't exist");
    }

    @Transactional
    @Override
    public boolean checkOrderAndCommit(UUID userId, UUID orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (order.getStatusPay().equals(StatusPay.PAID)) { //maybe unnecessary cos previously step is payment
                order.setApprovedFlag(Boolean.TRUE);
                Order save = orderRepository.save(order);
                log.info("Order approved < {} >", save.getId());

                //Из товаров на складе вычитается кол-во товара в заказе, если заказ сохранился
                itemService.decreaseItemQuantityOnWarehouse(order.getItems());

                return true;
            } else
                throw new IncorrectValueException("Order < " + order.getId() +  " > doesn't paid");
        } else
            throw new EntityNotFoundException("Order with id < " + orderId + " > doesn't exist");
    }

    @Override
    public OrderDTO changeApprovedFlagToTrue(UUID orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order found = orderOptional.get();
            if (!found.isApprovedFlag()) {
                found.setApprovedFlag(Boolean.TRUE);
                Order result = orderRepository.save(found);
                log.info("Order with < {} > change approve to < {} >", result.getId(), result.isApprovedFlag());
                return orderMapper.mapToDTO(result);
            } else
                log.info("Order already have approved status");
            return orderMapper.mapToDTO(found);
        } else
            throw new EntityNotFoundException("Order with given id < " + orderId + " > doesn't exist");
    }

    @Transactional
    @Override
    public String deleteOrderByOrderId(UUID orderId, UUID userId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            User foundUser = userService.findUserOrThrowException(userId);
            //собираем все заказы клиента что не равны id заказа, что мы хотим удалить
            List<Order> listWithoutOrderToDelete = foundUser
                    .getOrder().stream()
                    .filter(or -> !or.getId().equals(orderId))
                    .collect(Collectors.toList());
            foundUser.setOrder(listWithoutOrderToDelete);
            userService.saveUser(foundUser);
            orderRepository.deleteById(orderId);
            log.info("Order with id < {} > delete in user < {} >", orderId, userId);

            //если удаляем заказ, то надо и обратно вернуть товары на склад
            itemService.increaseItemQuantityOnWarehouse(order.getItems());

            //возможно стоит это сделать через кафку, асинхронно

            return DELETE;
        } else
            throw new EntityNotFoundException("Order with given id < " + orderId + " > doesn't exist");
    }





    //TODO check spring best practice - https://medium.com/codex/spring-boot-microservices-coding-style-guidelines-and-best-practices-1dec229161c8
    // 1. Modular Project Structure
    // 9. Global Exception Handling
    // 10. OpenAPI Specification Code Documentation
    // 11. Optimize Swagger UI Look and Feel
    // 12. Pagination and Sorting with Spring Data JPA
    // 15. Expose Health Check Endpoints ?
    // use Checkstyle and CodeStyle
    // 22. Liquibase for Database Schema Change Management ?
    // 23. Resilience4j for Circuit Breaker - for microservices
    // JUnit5, Cucumber, JaCoCo for test coverage
    // see all...


    //TODO Писать тесты для бизнес логики!!!! ведь так я могу убедиться в правильности своей логики!


}
