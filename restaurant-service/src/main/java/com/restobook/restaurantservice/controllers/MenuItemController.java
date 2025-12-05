package com.restobook.restaurantservice.controllers;

import com.restobook.restaurantservice.clients.AuthServiceClient;
import com.restobook.restaurantservice.dtos.request.CreateMenuItemRequest;
import com.restobook.restaurantservice.dtos.request.UpdateMenuItemRequest;
import com.restobook.restaurantservice.dtos.response.ApiResponse;
import com.restobook.restaurantservice.dtos.response.MenuItemResponse;
import com.restobook.restaurantservice.dtos.response.TokenValidationResponse;
import com.restobook.restaurantservice.enums.MenuCategory;
import com.restobook.restaurantservice.services.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "Gestion des menus des restaurants")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final AuthServiceClient authServiceClient;

    // Endpoints publiques

    @GetMapping
    @Operation(summary = "Menu complet", description = "Récupère tous les plats d'un restaurant")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getMenu(@PathVariable Long restaurantId) {
        log.info("Récupération du menu du restaurant: {}", restaurantId);
        List<MenuItemResponse> items = menuItemService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/available")
    @Operation(summary = "Plats disponibles", description = "Récupère uniquement les plats disponibles")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getAvailableMenu(@PathVariable Long restaurantId) {
        List<MenuItemResponse> items = menuItemService.getAvailableMenuItems(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Plats par catégorie")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getMenuByCategory(
            @PathVariable Long restaurantId,
            @PathVariable MenuCategory category) {

        List<MenuItemResponse> items = menuItemService.getMenuItemsByCategory(restaurantId, category);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des plats")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> searchMenu(
            @PathVariable Long restaurantId,
            @RequestParam String keyword) {

        List<MenuItemResponse> items = menuItemService.searchMenuItems(restaurantId, keyword);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/vegetarian")
    @Operation(summary = "Plats végétariens")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getVegetarianMenu(@PathVariable Long restaurantId) {
        List<MenuItemResponse> items = menuItemService.getVegetarianItems(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/vegan")
    @Operation(summary = "Plats vegan")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getVeganMenu(@PathVariable Long restaurantId) {
        List<MenuItemResponse> items = menuItemService.getVeganItems(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/gluten-free")
    @Operation(summary = "Plats sans gluten")
    public ResponseEntity<@NonNull ApiResponse<List<MenuItemResponse>>> getGlutenFreeMenu(@PathVariable Long restaurantId) {
        List<MenuItemResponse> items = menuItemService.getGlutenFreeItems(restaurantId);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Détails d'un plat")
    public ResponseEntity<@NonNull ApiResponse<MenuItemResponse>> getMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId) {

        MenuItemResponse item = menuItemService.getMenuItemById(itemId);
        return ResponseEntity.ok(ApiResponse.success(item));
    }

    // Endpoints authentifiés

    @PostMapping
    @Operation(summary = "Ajouter un plat", description = "Ajoute un nouveau plat au menu")
    public ResponseEntity<@NonNull ApiResponse<MenuItemResponse>> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateMenuItemRequest request,
            @RequestHeader("Authorization") String authHeader) {

        TokenValidationResponse tokenInfo = validateToken(authHeader);

        log.info("Création d'un plat pour le restaurant: {}", restaurantId);
        MenuItemResponse item = menuItemService.createMenuItem(restaurantId, request, tokenInfo.getUserId(), tokenInfo.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Plat ajouté au menu", item));
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Modifier un plat")
    public ResponseEntity<@NonNull ApiResponse<MenuItemResponse>> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateMenuItemRequest request,
            @RequestHeader("Authorization") String authHeader) {

         TokenValidationResponse tokenInfo = validateToken(authHeader);

        MenuItemResponse item = menuItemService.updateMenuItem(itemId, request, tokenInfo.getUserId(), tokenInfo.getRole());
        return ResponseEntity.ok(ApiResponse.success("Plat mis à jour", item));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Supprimer un plat")
    public ResponseEntity<@NonNull ApiResponse<Void>> deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @RequestHeader("Authorization") String authHeader) {

        TokenValidationResponse tokenInfo = validateToken(authHeader);

        menuItemService.deleteMenuItem(itemId, tokenInfo.getUserId(), tokenInfo.getRole());
        return ResponseEntity.ok(ApiResponse.success("Plat supprimé du menu"));
    }

    @PatchMapping("/{itemId}/toggle-availability")
    @Operation(summary = "Activer/Désactiver un plat")
    public ResponseEntity<@NonNull ApiResponse<MenuItemResponse>> toggleAvailability(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @RequestHeader("Authorization") String authHeader) {

        TokenValidationResponse tokenInfo = validateToken(authHeader);

        MenuItemResponse item = menuItemService.toggleAvailability(itemId, tokenInfo.getUserId(), tokenInfo.getRole());
        return ResponseEntity.ok(ApiResponse.success("Disponibilité mise à jour", item));
    }

    private TokenValidationResponse validateToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return authServiceClient.validateToken(token);
    }
}
