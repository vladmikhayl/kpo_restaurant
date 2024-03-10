package ru.hse.software.controller;

import ru.hse.software.models.Account;
import ru.hse.software.repository.RestaurantRepository;

public class VisitorMenuController implements UserMenuController {

    RestaurantRepository restaurantRepository;
    VisitorHandler visitorHandler;
    Account autorizedAccount;

    public VisitorMenuController(RestaurantRepository restaurantRepository, Account autorizedAccount) {
        this.restaurantRepository = restaurantRepository;
        visitorHandler = new VisitorHandler(restaurantRepository);
        this.autorizedAccount = autorizedAccount;
    }

    @Override
    public void processMenuItem(int menuItem) {
        switch (menuItem) {
            case 1:
                visitorHandler.processOrdersPrinting(autorizedAccount);
                break;
            case 2:
                visitorHandler.processOrderCreating(autorizedAccount);
                break;
            case 3:
                visitorHandler.processAddingDishes(autorizedAccount);
                break;
            case 4:
                visitorHandler.processOrderDeleting(autorizedAccount, restaurantRepository.getOrdersHandler());
                break;
            case 5:
                visitorHandler.processOrderPaying(autorizedAccount);
                break;
            case 6:
                visitorHandler.processFeedbackMaking(autorizedAccount);
                break;
            default:
                break;
        }
    }
}
