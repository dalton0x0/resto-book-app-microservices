package com.restobook.restaurantservice.repositories;

import com.restobook.restaurantservice.entities.MenuItem;
import com.restobook.restaurantservice.enums.MenuCategory;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<@NonNull MenuItem,@NonNull Long> {

    List<MenuItem> findByRestaurantIdOrderByDisplayOrderAscNameAsc(Long restaurantId);

    List<MenuItem> findByRestaurantIdAndCategoryOrderByDisplayOrderAscNameAsc(Long restaurantId, MenuCategory category);

    List<MenuItem> findByRestaurantIdAndAvailableTrueOrderByDisplayOrderAscNameAsc(Long restaurantId);

    List<MenuItem> findByRestaurantIdAndCategoryAndAvailableTrueOrderByDisplayOrderAscNameAsc(
            Long restaurantId, MenuCategory category);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MenuItem> searchByName(@Param("restaurantId") Long restaurantId, @Param("keyword") String keyword);

    List<MenuItem> findByRestaurantIdAndVegetarianTrueAndAvailableTrueOrderByDisplayOrderAsc(Long restaurantId);

    List<MenuItem> findByRestaurantIdAndVeganTrueAndAvailableTrueOrderByDisplayOrderAsc(Long restaurantId);

    List<MenuItem> findByRestaurantIdAndGlutenFreeTrueAndAvailableTrueOrderByDisplayOrderAsc(Long restaurantId);

    @Modifying
    @Query("DELETE FROM MenuItem m WHERE m.restaurant.id = :restaurantId")
    void deleteByRestaurantId(@Param("restaurantId") Long restaurantId);

    long countByRestaurantIdAndCategory(Long restaurantId, MenuCategory category);

    boolean existsByIdAndRestaurantId(Long id, Long restaurantId);

    Page<@NonNull MenuItem> findByRestaurantId(Long restaurantId, Pageable pageable);
}
