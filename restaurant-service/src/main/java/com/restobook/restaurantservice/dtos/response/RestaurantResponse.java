package com.restobook.restaurantservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restobook.restaurantservice.entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;
    private String imageUrl;
    private String cuisineType;
    private Integer totalCapacity;
    private Double averageRating;
    private Integer totalReviews;
    private Long ownerId;
    private Boolean active;
    private List<OpeningHoursResponse> openingHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RestaurantResponse fromEntity(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .address(restaurant.getAddress())
                .city(restaurant.getCity())
                .postalCode(restaurant.getPostalCode())
                .phone(restaurant.getPhone())
                .email(restaurant.getEmail())
                .imageUrl(restaurant.getImageUrl())
                .cuisineType(restaurant.getCuisineType())
                .totalCapacity(restaurant.getTotalCapacity())
                .averageRating(restaurant.getAverageRating())
                .totalReviews(restaurant.getTotalReviews())
                .ownerId(restaurant.getOwnerId())
                .active(restaurant.getActive())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build();
    }

    public static RestaurantResponse fromEntityWithHours(Restaurant restaurant) {
        RestaurantResponse response = fromEntity(restaurant);
        if (restaurant.getOpeningHours() != null && !restaurant.getOpeningHours().isEmpty()) {
            response.setOpeningHours(
                    restaurant.getOpeningHours().stream()
                            .map(OpeningHoursResponse::fromEntity)
                            .toList()
            );
        }
        return response;
    }
}
