package com.trevora.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@NoArgsConstructor
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long wishlistId;
    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @Column(name ="created_at",nullable = false)
    private Instant createdAt=Instant.now();
    @OneToMany(mappedBy = "wishlist",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<WishlistItem> items  = new ArrayList<>();
}
