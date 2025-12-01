package com.restobook.authservice.controllers;

import com.restobook.authservice.dtos.TokenValidationResponse;
import com.restobook.authservice.dtos.UserResponse;
import com.restobook.authservice.services.AuthService;
import com.restobook.authservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
@Tag(name = "Interne", description = "Endpoints internes pour la communication inter-services")
public class InternalController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/validate")
    @Operation(summary = "Valider un token", description = "Valide un token JWT et retourne les informations de l'utilisateur")
    public ResponseEntity<@NonNull TokenValidationResponse> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        log.debug("Requête de validation de token inter-service");

        String token = extractToken(authHeader);
        if (token == null) {
            log.warn("Token manquant dans l'en-tête Authorization");
            return ResponseEntity.ok(TokenValidationResponse.invalid("Token manquant"));
        }

        TokenValidationResponse response = authService.validateToken(token);

        if (response.getValid()) {
            log.debug("Token valide pour l'utilisateur ID: {}", response.getUserId());
        } else {
            log.warn("Validation du token échouée: {}", response.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Récupérer un utilisateur", description = "Récupère les informations d'un utilisateur par son ID (usage interne)")
    public ResponseEntity<@NonNull UserResponse> getUserById(@PathVariable Long id) {
        log.debug("Requête interne - Récupération de l'utilisateur ID: {}", id);

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/email/{email}")
    @Operation(summary = "Récupérer un utilisateur par email", description = "Récupère les informations d'un utilisateur par son email (usage interne)")
    public ResponseEntity<@NonNull UserResponse> getUserByEmail(@PathVariable String email) {
        log.debug("Requête interne - Récupération de l'utilisateur par email: {}", email);

        UserResponse user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}/exists")
    @Operation(summary = "Vérifier l'existence d'un utilisateur", description = "Vérifie si un utilisateur existe par son ID")
    public ResponseEntity<@NonNull Boolean> userExists(@PathVariable Long id) {
        log.debug("Requête interne - Vérification de l'existence de l'utilisateur ID: {}", id);

        try {
            userService.getUserById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
