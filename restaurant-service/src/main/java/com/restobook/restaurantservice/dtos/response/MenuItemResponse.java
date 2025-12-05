package com.restobook.restaurantservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restobook.restaurantservice.entities.MenuItem;
import com.restobook.restaurantservice.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemResponse {

    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private MenuCategory category;
    private String imageUrl;
    private String allergens;
    private String nutritionalInfo;
    private Boolean available;
    private Boolean vegetarian;
    private Boolean vegan;
    private Boolean glutenFree;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuItemResponse fromEntity(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .restaurantId(menuItem.getRestaurant().getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory())
                .imageUrl(menuItem.getImageUrl())
                .allergens(menuItem.getAllergens())
                .nutritionalInfo(menuItem.getNutritionalInfo())
                .available(menuItem.getAvailable())
                .vegetarian(menuItem.getVegetarian())
                .vegan(menuItem.getVegan())
                .glutenFree(menuItem.getGlutenFree())
                .displayOrder(menuItem.getDisplayOrder())
                .createdAt(menuItem.getCreatedAt())
                .updatedAt(menuItem.getUpdatedAt())
                .build();
    }
}
