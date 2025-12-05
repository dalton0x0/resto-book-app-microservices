package com.restobook.restaurantservice.dtos.request;

import com.restobook.restaurantservice.enums.MenuCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuItemRequest {

    @NotBlank(message = "Le nom du plat est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    @DecimalMax(value = "9999.99", message = "Le prix ne peut pas dépasser 9999.99€")
    private BigDecimal price;

    @NotNull(message = "La catégorie est obligatoire")
    private MenuCategory category;

    @Size(max = 500, message = "L'URL de l'image ne peut pas dépasser 500 caractères")
    private String imageUrl;

    @Size(max = 200, message = "Les allergènes ne peuvent pas dépasser 200 caractères")
    private String allergens;

    @Size(max = 500, message = "Les informations nutritionnelles ne peuvent pas dépasser 500 caractères")
    private String nutritionalInfo;

    @Builder.Default
    private Boolean available = true;

    @Builder.Default
    private Boolean vegetarian = false;

    @Builder.Default
    private Boolean vegan = false;

    @Builder.Default
    private Boolean glutenFree = false;

    private Integer displayOrder;
}
