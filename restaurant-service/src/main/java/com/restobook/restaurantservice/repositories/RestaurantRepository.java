package com.restobook.restaurantservice.repositories;

import com.restobook.restaurantservice.entities.Restaurant;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<@NonNull Restaurant,@NonNull Long> {

    Page<@NonNull Restaurant> findByCityIgnoreCaseAndActiveTrue(String city, Pageable pageable);

    List<@NonNull Restaurant> findByOwnerId(Long ownerId);

    Page<@NonNull Restaurant> findByOwnerId(Long ownerId, Pageable pageable);

    Page<@NonNull Restaurant> findByActiveTrue(Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.active = true AND " +
            "(LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.cuisineType) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<@NonNull Restaurant> searchRestaurants(@Param("keyword") String keyword, Pageable pageable);

    Page<@NonNull Restaurant> findByCuisineTypeIgnoreCaseAndActiveTrue(String cuisineType, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.active = true AND r.totalReviews > 0 ORDER BY r.averageRating DESC")
    Page<@NonNull Restaurant> findTopRated(Pageable pageable);

    boolean existsByIdAndOwnerId(Long id, Long ownerId);

    long countByOwnerId(Long ownerId);

    Optional<Restaurant> findByIdAndActiveTrue(Long id);

    // Recherche avancée
    @Query("SELECT r FROM Restaurant r WHERE r.active = true " +
            "AND (:city IS NULL OR LOWER(r.city) = LOWER(:city)) " +
            "AND (:cuisineType IS NULL OR LOWER(r.cuisineType) = LOWER(:cuisineType)) " +
            "AND (:minRating IS NULL OR r.averageRating >= :minRating)")
    Page<@NonNull Restaurant> findByFilters(
            @Param("city") String city,
            @Param("cuisineType") String cuisineType,
            @Param("minRating") Double minRating,
            Pageable pageable
    );

    @Query("SELECT DISTINCT r.city FROM Restaurant r WHERE r.active = true ORDER BY r.city")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT r.cuisineType FROM Restaurant r WHERE r.active = true AND r.cuisineType IS NOT NULL ORDER BY r.cuisineType")
    List<String> findDistinctCuisineTypes();
}
