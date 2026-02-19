package com.ecommerce.vichika.service;

import com.ecommerce.vichika.dto.OrderDto;
import com.ecommerce.vichika.exceptions.ResourceNotFoundExceptions;
import com.ecommerce.vichika.mapper.OrderMapper;
import com.ecommerce.vichika.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream()
                .map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Order with id " + orderId + " not found"));
        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You are not allowed to access this resource");
        }

        return orderMapper.toDto(order);
    }
}
