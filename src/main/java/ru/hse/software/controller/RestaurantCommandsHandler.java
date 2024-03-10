package ru.hse.software.controller;

import ru.hse.software.models.Account;

public interface RestaurantCommandsHandler {
    Account processMenuItem(int menuItem);
}
