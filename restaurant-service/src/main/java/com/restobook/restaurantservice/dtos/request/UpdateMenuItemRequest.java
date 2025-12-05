package com.restobook.restaurantservice.dtos.request;

import com.restobook.restaurantservice.enums.MenuCategory;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMenuItemRequest {

    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    @DecimalMax(value = "9999.99", message = "Le prix ne peut pas dépasser 9999.99€")
    private BigDecimal price;

    private MenuCategory category;

    @Size(max = 500, message = "L'URL de l'image ne peut pas dépasser 500 caractères")
    private String imageUrl;

    @Size(max = 200, message = "Les allergènes ne peuvent pas dépasser 200 caractères")
    private String allergens;

    @Size(max = 500, message = "Les informations nutritionnelles ne peuvent pas dépasser 500 caractères")
    private String nutritionalInfo;

    private Boolean available;
    private Boolean vegetarian;
    private Boolean vegan;
    private Boolean glutenFree;
    private Integer displayOrder;
}
