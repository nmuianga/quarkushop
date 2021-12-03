package mz.co.muianga.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.model.Cart;
import mz.co.muianga.model.Order;
import mz.co.muianga.model.OrderStatus;
import mz.co.muianga.repository.CartRepository;
import mz.co.muianga.repository.OrderRepository;
import mz.co.muianga.repository.PaymentRepository;
import mz.co.muianga.resources.dto.OrderDto;
import mz.co.muianga.resources.dto.OrderItemDto;

@Slf4j
@ApplicationScoped
@Transactional
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    CartRepository cartRepository;

    public List<OrderDto> findAll() {
        log.debug("Request to get all Orders");

        return this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto findById(Long id) {
        log.debug("Request to get Order : {}", id);

        return this.orderRepository.findById(id)
                .map(OrderService::mapToDto)
                .orElse(null);
    }

    public List<OrderDto> findAllByUser(Long id) {
        return this.orderRepository.findByCartCustomerId(id)
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto create(OrderDto orderDto) {
        log.debug("Request to create Order : {}", orderDto);

        Long cartId = orderDto.getCart().getId();

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalStateException("The Cart with ID[" + cartId + "] was not found"));

        return mapToDto(this.orderRepository.save(new Order(BigDecimal.ZERO,
                OrderStatus.CREATION, null, null,
                AddressService.createFromDto(orderDto.getShipmentAddress()),
                Collections.emptySet(), cart)));
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("The Order with ID[" + id + "] does not exist!"));

        Optional.ofNullable(order.getPayment())
                .ifPresent(paymentRepository::delete);

        orderRepository.delete(order);
    }

    public boolean existsById(Long id) {
        return this.orderRepository.existsById(id);
    }

    public static OrderDto mapToDto(Order order) {
        Set<OrderItemDto> orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemService::mapToDto)
                .collect(Collectors.toSet());

        return new OrderDto(order.getId(),
                order.getPrice(),
                order.getStatus().name(),
                order.getShipped(),
                order.getPayment() != null ? order.getPayment().getId() : null,
                AddressService.mapToDto(order.getShipmentAddress()),
                orderItems,
                CartService.mapToDto(order.getCart()));
    }
}
