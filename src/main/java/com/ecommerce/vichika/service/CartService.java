package com.ecommerce.vichika.service;

import com.ecommerce.vichika.dto.CartDto;
import com.ecommerce.vichika.dto.CartItemDto;
import com.ecommerce.vichika.dto.UpdateCartItemRequest;
import com.ecommerce.vichika.entities.Cart;
import com.ecommerce.vichika.exceptions.ResourceNotFoundExceptions;
import com.ecommerce.vichika.mapper.CartMapper;
import com.ecommerce.vichika.repository.CartRepository;
import com.ecommerce.vichika.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId , Long productId){
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Cart not found"));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product not found"));
        var cartItems = cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toItemDto(cartItems);
    }
    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Cart not found"));
        return cartMapper.toDto(cart);
    }
    public CartDto updateCart(UUID cartId, Long productId, UpdateCartItemRequest request) {
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Cart not found"));
        var cartItems = cart.getItemFromCart(productId);
        if (cartItems == null) {
            throw new ResourceNotFoundExceptions("cart item not found");
        }
        cartItems.setQuantity(request.quantity());
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public void deleteCartFromProduct(UUID cartId, Long productId){
        var cart = cartRepository.getCartWithItems(cartId).
                orElseThrow(() -> new ResourceNotFoundExceptions("Cart not found"));
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    public void clearCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Cart not found"));
        cart.clearCart();
        cartRepository.save(cart);
    }

}
