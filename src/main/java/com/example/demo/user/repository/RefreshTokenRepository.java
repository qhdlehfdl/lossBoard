package com.example.demo.user.repository;

import com.example.demo.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {

    boolean existsByUserIdAndRefreshToken(String userId, String refreshToken);

   void deleteByUserIdAndRefreshToken(String userId, String refreshToken);

}
