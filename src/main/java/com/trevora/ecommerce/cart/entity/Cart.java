package com.trevora.ecommerce.cart.entity;

import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.enums.CartStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id",nullable = false)
    private Long cartId;
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt=Instant.now();
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CartStatus status;
    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CartItem> cartItem=new ArrayList<>();
}
