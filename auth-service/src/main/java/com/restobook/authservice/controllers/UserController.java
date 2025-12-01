package com.restobook.authservice.controllers;

import com.restobook.authservice.dtos.ApiResponse;
import com.restobook.authservice.dtos.ChangePasswordRequest;
import com.restobook.authservice.dtos.UpdateUserRequest;
import com.restobook.authservice.dtos.UserResponse;
import com.restobook.authservice.security.UserDetailsImpl;
import com.restobook.authservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Endpoints pour la gestion du profil utilisateur")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Mon profil", description = "Récupère le profil de l'utilisateur connecté")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        log.info("Récupération du profil pour: {}", userDetails.getEmail());

        UserResponse userResponse = userService.getCurrentUser(userDetails.getId());

        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @PutMapping("/me")
    @Operation(summary = "Modifier mon profil", description = "Met à jour le profil de l'utilisateur connecté")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> updateCurrentUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdateUserRequest request) {

        log.info("Mise à jour du profil pour: {}", userDetails.getEmail());

        UserResponse userResponse = userService.updateCurrentUser(userDetails.getId(), request);

        log.info("Profil mis à jour avec succès pour: {}", userDetails.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Profil mis à jour avec succès", userResponse));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Changer mon mot de passe", description = "Change le mot de passe de l'utilisateur connecté")
    public ResponseEntity<@NonNull ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {

        log.info("Changement de mot de passe pour: {}", userDetails.getEmail());

        userService.changePassword(userDetails.getId(), request);

        log.info("Mot de passe changé avec succès pour: {}", userDetails.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Mot de passe changé avec succès. Veuillez vous reconnecter."));
    }
}
