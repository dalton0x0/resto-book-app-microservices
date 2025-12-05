package com.restobook.restaurantservice.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantRequest {

    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
    private String address;

    @Size(max = 100, message = "La ville ne peut pas dépasser 100 caractères")
    private String city;

    @Size(max = 10, message = "Le code postal ne peut pas dépasser 10 caractères")
    private String postalCode;

    @Pattern(regexp = "^(\\+33|0)[1-9](\\d{8})$", message = "Format de téléphone invalide")
    private String phone;

    @Email(message = "Format d'email invalide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    private String email;

    @Size(max = 500, message = "L'URL de l'image ne peut pas dépasser 500 caractères")
    private String imageUrl;

    @Size(max = 50, message = "Le type de cuisine ne peut pas dépasser 50 caractères")
    private String cuisineType;

    @Min(value = 1, message = "La capacité doit être d'au moins 1 place")
    @Max(value = 500, message = "La capacité ne peut pas dépasser 500 places")
    private Integer totalCapacity;
}
