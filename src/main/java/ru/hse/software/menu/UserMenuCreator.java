package ru.hse.software.menu;

import ru.hse.software.controller.AdminMenuController;
import ru.hse.software.controller.UserMenuController;
import ru.hse.software.controller.VisitorMenuController;
import ru.hse.software.menu.printers.AdminMenuPrinter;
import ru.hse.software.menu.printers.VisitorMenuPrinter;
import ru.hse.software.repository.RestaurantRepository;
import ru.hse.software.models.Account;

public class UserMenuCreator {
    RestaurantRepository restaurantDataBase;

    public UserMenuCreator(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
    }

    public Menu createUserMenu (Account authorizedAccount) {
        if (authorizedAccount.isAdmin()) {
            return new Menu(new AdminMenuPrinter(restaurantDataBase),
                    0, 6, restaurantDataBase);
        } else {
            return new Menu(new VisitorMenuPrinter(restaurantDataBase),
                    0, 6, restaurantDataBase);
        }
    }

    public UserMenuController createUserMenuController(Account authorizedAccount) {
        if (authorizedAccount.isAdmin()) {
            return new AdminMenuController(restaurantDataBase);
        } else {
            return new VisitorMenuController(restaurantDataBase, authorizedAccount);
        }
    }
}
