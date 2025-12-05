package com.restobook.restaurantservice.services;

import com.restobook.restaurantservice.dtos.request.CreateMenuItemRequest;
import com.restobook.restaurantservice.dtos.request.UpdateMenuItemRequest;
import com.restobook.restaurantservice.dtos.response.MenuItemResponse;
import com.restobook.restaurantservice.enums.MenuCategory;

import java.util.List;

public interface MenuItemService {

    // CRUD 

    MenuItemResponse createMenuItem(Long restaurantId, CreateMenuItemRequest request, Long userId, String role);

    MenuItemResponse getMenuItemById(Long id);

    MenuItemResponse updateMenuItem(Long id, UpdateMenuItemRequest request, Long userId, String role);

    void deleteMenuItem(Long id, Long userId, String role);

    // Recherche 

    List<MenuItemResponse> getMenuItemsByRestaurant(Long restaurantId);

    List<MenuItemResponse> getMenuItemsByCategory(Long restaurantId, MenuCategory category);

    List<MenuItemResponse> getAvailableMenuItems(Long restaurantId);

    List<MenuItemResponse> searchMenuItems(Long restaurantId, String keyword);

    List<MenuItemResponse> getVegetarianItems(Long restaurantId);

    List<MenuItemResponse> getVeganItems(Long restaurantId);

    List<MenuItemResponse> getGlutenFreeItems(Long restaurantId);

    MenuItemResponse toggleAvailability(Long id, Long userId, String role);
}
