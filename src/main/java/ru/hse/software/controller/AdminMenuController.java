package ru.hse.software.controller;

import ru.hse.software.repository.RestaurantRepository;

public class AdminMenuController implements UserMenuController {
    RestaurantRepository restaurantRepository;
    AdminHandler adminHandler;

    public AdminMenuController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        adminHandler = new AdminHandler(restaurantRepository);
    }

    @Override
    public void processMenuItem(int menuItem) {
        switch (menuItem) {
            case 1:
                adminHandler.processDishAdding();
                break;
            case 2:
                adminHandler.processDishDeleting();
                break;
            case 3:
                adminHandler.processPriceChanging();
                break;
            case 4:
                adminHandler.processQuantityChanging();
                break;
            case 5:
                adminHandler.processSecsToCookChanging();
                break;
            case 6:
                adminHandler.processInfoShowing();
                break;
            default:
                break;
        }
    }
}
