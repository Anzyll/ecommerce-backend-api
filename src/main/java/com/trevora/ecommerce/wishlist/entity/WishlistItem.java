package com.trevora.ecommerce.wishlist.entity;

import com.trevora.ecommerce.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "wishlist_items",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_wishlist_item_product",
                        columnNames = {"wishlist_id", "product_id"}
                )})
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "wishlist_id",nullable = false)
    private Wishlist wishlist;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
}
