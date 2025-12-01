package com.restobook.authservice.repositories;

import com.restobook.authservice.entities.RefreshToken;
import com.restobook.authservice.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken,@NonNull  Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user = :user")
    void revokeAllByUser(@Param("user") User user);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.revoked = true, r.expiryDate = CURRENT_TIMESTAMP WHERE r.user.id = :userId")
    void revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiryDate < :now")
    int deleteExpiredTokens(@Param("now") Instant now);

    @Query("SELECT COUNT(r) FROM RefreshToken r WHERE r.user = :user AND r.revoked = false AND r.expiryDate > :now")
    long countActiveTokensByUser(@Param("user") User user, @Param("now") Instant now);
}
