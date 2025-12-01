package com.restobook.authservice.services;

import com.restobook.authservice.dtos.*;

public interface AuthService {

    /**
     * Inscription d'un nouvel utilisateur avec le rôle CLIENT par défaut
     */
    UserResponse register(RegisterRequest request);

    /**
     * Connexion d'un utilisateur
     */
    AuthResponse login(LoginRequest request);

    /**
     * Rafraîchissement du token d'accès
     */
    AuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Déconnexion - révocation du refresh token
     */
    void logout(String refreshToken);

    /**
     * Déconnexion de toutes les sessions d'un utilisateur
     */
    void logoutAll(Long userId);

    /**
     * Validation d'un token (pour les appels inter-services)
     */
    TokenValidationResponse validateToken(String token);
}
