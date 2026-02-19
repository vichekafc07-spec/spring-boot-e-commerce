package com.ecommerce.vichika.mapper;

import com.ecommerce.vichika.dto.OrderDto;
import com.ecommerce.vichika.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
