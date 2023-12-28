package com.spring.finaldemo.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role ;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getters and setters


}