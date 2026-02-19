package com.ecommerce.vichika.mapper;

import com.ecommerce.vichika.dto.CartDto;
import com.ecommerce.vichika.dto.CartItemDto;
import com.ecommerce.vichika.entities.Cart;
import com.ecommerce.vichika.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toItemDto(CartItem cartItem);
}
