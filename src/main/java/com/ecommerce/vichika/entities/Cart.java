package com.ecommerce.vichika.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created",insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE,fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    public BigDecimal getTotalPrice() {
/*        BigDecimal total = BigDecimal.ZERO;
//        for (CartItem item : items) {
//            total = total.add(item.getTotalPrice());
       } */
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItemFromCart(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItems = getItemFromCart(product.getId());
        if (cartItems != null) {
            cartItems.setQuantity(cartItems.getQuantity() + 1);
        }else {
            cartItems = new CartItem();
            cartItems.setProduct(product);
            cartItems.setQuantity(1);
            cartItems.setCart(this);
            items.add(cartItems);
        }
        return cartItems;
    }

    public void removeItem(Long productId) {
        var cartItem = getItemFromCart(productId);
        if (cartItem != null){
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}