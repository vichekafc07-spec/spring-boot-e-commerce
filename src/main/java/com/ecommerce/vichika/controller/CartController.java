package com.ecommerce.vichika.controller;

import com.ecommerce.vichika.dto.AddItemToCartRequest;
import com.ecommerce.vichika.dto.CartDto;
import com.ecommerce.vichika.dto.CartItemDto;
import com.ecommerce.vichika.dto.UpdateCartItemRequest;
import com.ecommerce.vichika.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart.")
    public ResponseEntity<CartItemDto> addToCart(@Parameter(description = "The Id of the cart.")
            @PathVariable UUID cartId, @RequestBody AddItemToCartRequest request) {
        var cartItemDto = cartService.addToCart(cartId, request.productId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateItem(@PathVariable("cartId") UUID cartId,
                                                  @PathVariable("productId") Long productId,
                                                 @Valid @RequestBody UpdateCartItemRequest request) {
        var cartItems = cartService.updateCart(cartId,productId,request);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable("cartId") UUID cartId,
                                           @PathVariable Long productId) {
        cartService.deleteCartFromProduct(cartId,productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

}
