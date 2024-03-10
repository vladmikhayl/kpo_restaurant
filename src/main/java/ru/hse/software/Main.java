package ru.hse.software;

import ru.hse.software.controller.AuthenticationMenuController;
import ru.hse.software.controller.RestaurantCommandsHandler;
import ru.hse.software.controller.UserMenuController;
import ru.hse.software.menu.Menu;
import ru.hse.software.menu.UserMenuCreator;
import ru.hse.software.menu.printers.AuthenticationMenuPrinter;
import ru.hse.software.exceptions.DataBaseSerializingException;
import ru.hse.software.orders.Order;
import ru.hse.software.repository.RestaurantDataBase;
import ru.hse.software.repository.RestaurantRepository;
import ru.hse.software.models.Account;

public class Main {
    public static void main(String[] args) {

        // Чтение информации о ресторане из файла
        RestaurantRepository restaurantDataBase;
        try {
            restaurantDataBase = new RestaurantDataBase(args[0]);
        } catch (DataBaseSerializingException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
            return;
        }

        Menu authenticationMenu = new Menu(new AuthenticationMenuPrinter(),
                0, 2, restaurantDataBase);
        RestaurantCommandsHandler authenticationMenuController = new AuthenticationMenuController(restaurantDataBase);
        restaurantDataBase.getOrdersHandler().setActive(true);

        while (true) {
            // Авторизация
            Account authorizedAccount;
            int menuItem = -1;
            do {
                if (menuItem == 0) {
                    // Остановка всех потоков заказов
                    restaurantDataBase.getOrdersHandler().setActive(false);
                    for (Order order : restaurantDataBase.getOrdersHandler().getOrdersInProcessing()) {
                        order.setActive(false);
                    }
                    for (Order order : restaurantDataBase.getOrdersHandler().getOrdersInCooking()) {
                        order.setActive(false);
                    }
                    return;
                }
                authenticationMenu.printMenu();
                menuItem = authenticationMenu.selectMenuItem();
                authorizedAccount = authenticationMenuController.processMenuItem(menuItem);
            } while (authorizedAccount == null);

            System.out.println("\nВы успешно авторизовались!");

            UserMenuCreator userMenuCreator = new UserMenuCreator(restaurantDataBase);
            Menu userMenu = userMenuCreator.createUserMenu(authorizedAccount);
            UserMenuController userMenuController = userMenuCreator.createUserMenuController(authorizedAccount);

            // Обработка действий авторизированного пользователя
            menuItem = -1;
            while (menuItem != 0) {
                userMenu.printMenu();
                menuItem = userMenu.selectMenuItem();
                userMenuController.processMenuItem(menuItem);
            }
        }

    }
}