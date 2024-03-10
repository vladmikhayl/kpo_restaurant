package ru.hse.software.repository;

import ru.hse.software.models.Account;
import ru.hse.software.models.Restaurant;
import ru.hse.software.orders.OrdersHandler;

import java.io.FileNotFoundException;

public interface RestaurantRepository {
    boolean checkDoesAccountWithSuchLoginExists(String login);
    Account getAccount(String login, String hashedPass);
    Account addAccount(String login, String hashedPass, boolean isAdmin);
    void saveRestaurantInfo() throws FileNotFoundException;
    Restaurant getRestaurant();
    OrdersHandler getOrdersHandler();

}
