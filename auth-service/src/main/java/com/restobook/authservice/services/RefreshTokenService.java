package com.restobook.authservice.services;

import com.restobook.authservice.entities.RefreshToken;
import com.restobook.authservice.entities.User;

public interface RefreshTokenService {

    /**
     * Crée un nouveau refresh token pour un utilisateur
     */
    RefreshToken createRefreshToken(User user);

    /**
     * Valide et retourne le refresh token
     */
    RefreshToken validateRefreshToken(String token);

    /**
     * Révoque un refresh token spécifique
     */
    void revokeRefreshToken(String token);

    /**
     * Révoque tous les refresh tokens d'un utilisateur
     */
    void revokeAllUserTokens(Long userId);

    /**
     * Supprime les tokens expirés
     */
    int deleteExpiredTokens();
}
