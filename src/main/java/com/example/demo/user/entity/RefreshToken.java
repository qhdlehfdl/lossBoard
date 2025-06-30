package com.example.demo.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="refresh_token")
@Getter @Setter @NoArgsConstructor
public class RefreshToken {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    public RefreshToken(String userId, String refreshToken){
        this.userId=userId;
        this.refreshToken=refreshToken;
    }
}
