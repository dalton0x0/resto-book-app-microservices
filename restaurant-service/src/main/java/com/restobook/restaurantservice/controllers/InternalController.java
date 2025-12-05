package com.restobook.restaurantservice.controllers;

import com.restobook.restaurantservice.dtos.response.ApiResponse;
import com.restobook.restaurantservice.dtos.response.RestaurantResponse;
import com.restobook.restaurantservice.enums.DayOfWeek;
import com.restobook.restaurantservice.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
@Tag(name = "Internal", description = "Endpoints internes pour la communication inter-services")
public class InternalController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants/{id}/exists")
    @Operation(summary = "Vérifier l'existence d'un restaurant")
    public ResponseEntity<@NonNull Boolean> restaurantExists(@PathVariable Long id) {
        log.debug("Vérification de l'existence du restaurant: {}", id);
        boolean exists = restaurantService.restaurantExists(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/restaurants/{id}/capacity")
    @Operation(summary = "Récupérer la capacité d'un restaurant")
    public ResponseEntity<@NonNull Integer> getRestaurantCapacity(@PathVariable Long id) {
        log.debug("Récupération de la capacité du restaurant: {}", id);
        Integer capacity = restaurantService.getRestaurantCapacity(id);
        return ResponseEntity.ok(capacity);
    }

    @GetMapping("/restaurants/{id}")
    @Operation(summary = "Récupérer les informations d'un restaurant")
    public ResponseEntity<@NonNull RestaurantResponse> getRestaurant(@PathVariable Long id) {
        log.debug("Récupération du restaurant: {}", id);
        RestaurantResponse restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurants/{id}/is-open")
    @Operation(summary = "Vérifier si un restaurant est ouvert")
    public ResponseEntity<@NonNull Boolean> isRestaurantOpen(
            @PathVariable Long id,
            @RequestParam DayOfWeek dayOfWeek,
            @RequestParam LocalTime time) {

        log.debug("Vérification ouverture du restaurant {} - {} à {}", id, dayOfWeek, time);
        boolean isOpen = restaurantService.isRestaurantOpen(id, dayOfWeek, time);
        return ResponseEntity.ok(isOpen);
    }

    @PutMapping("/restaurants/{id}/rating")
    @Operation(summary = "Mettre à jour la note d'un restaurant", description = "Appelé par le Review Service")
    public ResponseEntity<@NonNull ApiResponse<Void>> updateRating(
            @PathVariable Long id,
            @RequestParam Double rating,
            @RequestParam Integer totalReviews) {

        log.info("Mise à jour de la note du restaurant {} - Note: {}, Avis: {}", id, rating, totalReviews);
        restaurantService.updateRestaurantRating(id, rating, totalReviews);
        return ResponseEntity.ok(ApiResponse.success("Note mise à jour"));
    }
}
