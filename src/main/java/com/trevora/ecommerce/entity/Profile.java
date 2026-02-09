package com.trevora.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter@Setter@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;
    @Column(nullable = false,name = "full_name")
    @Size(min = 3,max = 50)
    private  String fullName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}
