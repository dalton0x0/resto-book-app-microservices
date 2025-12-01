package com.restobook.authservice.controllers;

import com.restobook.authservice.dtos.*;
import com.restobook.authservice.enums.RoleName;
import com.restobook.authservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administration", description = "Endpoints d'administration des utilisateurs (ADMIN uniquement)")
public class AdminController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Liste des utilisateurs", description = "Récupère la liste paginée de tous les utilisateurs")
    public ResponseEntity<@NonNull ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Récupération de tous les utilisateurs - Page: {}, Taille: {}", page, size);

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<UserResponse> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des utilisateurs", description = "Recherche des utilisateurs par mot-clé")
    public ResponseEntity<@NonNull ApiResponse<PageResponse<UserResponse>>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Recherche d'utilisateurs avec le mot-clé: {}", keyword);

        Pageable pageable = PageRequest.of(page, size);
        PageResponse<UserResponse> users = userService.searchUsers(keyword, pageable);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/role/{roleName}")
    @Operation(summary = "Utilisateurs par rôle", description = "Récupère les utilisateurs ayant un rôle spécifique")
    public ResponseEntity<@NonNull ApiResponse<PageResponse<UserResponse>>> getUsersByRole(
            @PathVariable RoleName roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Récupération des utilisateurs avec le rôle: {}", roleName);

        Pageable pageable = PageRequest.of(page, size);
        PageResponse<UserResponse> users = userService.getUsersByRole(roleName, pageable);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un utilisateur", description = "Récupère les détails d'un utilisateur par son ID")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        log.info("Récupération de l'utilisateur ID: {}", id);

        UserResponse user = userService.getUserById(id);

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Crée un nouvel utilisateur avec un rôle spécifique")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Création d'un utilisateur: {} avec le rôle: {}", request.getEmail(), request.getRoleName());

        UserResponse user = userService.createUser(request);

        log.info("Utilisateur créé avec succès: {}", request.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Utilisateur créé avec succès", user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un utilisateur", description = "Met à jour les informations d'un utilisateur")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        log.info("Mise à jour de l'utilisateur ID: {}", id);

        UserResponse user = userService.updateUser(id, request);

        log.info("Utilisateur mis à jour avec succès: {}", user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Utilisateur mis à jour avec succès", user));
    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Modifier le rôle", description = "Change le rôle d'un utilisateur")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {

        log.info("Modification du rôle de l'utilisateur ID: {} vers: {}", id, request.getRoleName());

        UserResponse user = userService.updateUserRole(id, request);

        log.info("Rôle mis à jour avec succès pour: {}", user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Rôle mis à jour avec succès", user));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Activer un utilisateur", description = "Active le compte d'un utilisateur")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> enableUser(@PathVariable Long id) {
        log.info("Activation de l'utilisateur ID: {}", id);

        UserResponse user = userService.enableUser(id);

        log.info("Utilisateur activé: {}", user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Utilisateur activé avec succès", user));
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Désactiver un utilisateur", description = "Désactive le compte d'un utilisateur")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> disableUser(@PathVariable Long id) {
        log.info("Désactivation de l'utilisateur ID: {}", id);

        UserResponse user = userService.disableUser(id);

        log.info("Utilisateur désactivé: {}", user.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Utilisateur désactivé avec succès", user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime définitivement un utilisateur")
    public ResponseEntity<@NonNull ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        log.info("Suppression de l'utilisateur ID: {}", id);

        userService.deleteUser(id);

        log.info("Utilisateur supprimé avec succès ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur supprimé avec succès"));
    }
}
