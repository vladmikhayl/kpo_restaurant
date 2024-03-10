package ru.hse.software.controller;

import ru.hse.software.authentication.AuthenticationHandler;
import ru.hse.software.exceptions.DataBaseSerializingException;
import ru.hse.software.repository.RestaurantRepository;
import ru.hse.software.models.Account;

import java.io.FileNotFoundException;

public class AuthenticationMenuController implements RestaurantCommandsHandler {
    RestaurantRepository restaurantDataBase;
    AuthenticationHandler authenticationHandler;

    public AuthenticationMenuController(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
        authenticationHandler = new AuthenticationHandler(restaurantDataBase);
    }

    @Override
    public Account processMenuItem(int menuItem) {
        return switch (menuItem) {
            case 1 -> authenticationHandler.processLogIn();
            case 2 -> authenticationHandler.processRegistration();
            default -> {
                try {
                    restaurantDataBase.saveRestaurantInfo();
                } catch (DataBaseSerializingException | FileNotFoundException ex) {
                    System.out.printf("Ошибка! %s\n", ex.getMessage());
                }
                yield null;
            }
        };
    }
}
