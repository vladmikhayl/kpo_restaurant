package ru.hse.software.controller;

import ru.hse.software.exceptions.DataBaseSerializingException;
import ru.hse.software.menu.MenuReader;
import ru.hse.software.menu.printers.AdminMenuPrinter;
import ru.hse.software.models.Dish;
import ru.hse.software.repository.RestaurantRepository;

import java.io.FileNotFoundException;

public class AdminHandler {
    RestaurantRepository restaurantRepository;
    MenuReader menuReader;

    public AdminHandler(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        menuReader = new MenuReader(restaurantRepository);
    }

    public void processDishAdding() {
        Dish dish = Dish.builder()
                .name(menuReader.getDishName())
                .price(menuReader.getDishPrice())
                .quantity(menuReader.getDishQuantity())
                .secToCook(menuReader.getDishSecsToCook())
                .build();
        restaurantRepository.getRestaurant().getDishesMenu().add(dish);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("Блюдо успешно добавлено!");
    }

    public void processDishDeleting() {
        if (restaurantRepository.getRestaurant().getDishesMenu().isEmpty()) {
            System.out.println("\nЭто действие невозможно! В меню нет ни одного блюда :(");
            return;
        }
        int dishNumber = menuReader.getDishNumber("\nВведите порядковый номер блюда, которое нужно удалить: ");
        restaurantRepository.getRestaurant().getDishesMenu().remove(dishNumber-1);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("Блюдо успешно удалено!");
    }

    public void processPriceChanging() {
        if (restaurantRepository.getRestaurant().getDishesMenu().isEmpty()) {
            System.out.println("\nЭто действие невозможно! В меню нет ни одного блюда :(");
            return;
        }
        int dishNumber = menuReader.getDishNumber("\nВведите порядковый номер блюда, которое нужно изменить: ");
        double price = menuReader.getDishPrice();
        restaurantRepository.getRestaurant().getDishesMenu().get(dishNumber-1).setPrice(price);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("Цена успешно изменена!");
    }

    public void processQuantityChanging() {
        if (restaurantRepository.getRestaurant().getDishesMenu().isEmpty()) {
            System.out.println("\nЭто действие невозможно! В меню нет ни одного блюда :(");
            return;
        }
        int dishNumber = menuReader.getDishNumber("\nВведите порядковый номер блюда, которое нужно изменить: ");
        int quantity = menuReader.getDishQuantity();
        restaurantRepository.getRestaurant().getDishesMenu().get(dishNumber-1).setQuantity(quantity);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("Количество успешно изменено!");
    }

    public void processSecsToCookChanging() {
        if (restaurantRepository.getRestaurant().getDishesMenu().isEmpty()) {
            System.out.println("\nЭто действие невозможно! В меню нет ни одного блюда :(");
            return;
        }
        int dishNumber = menuReader.getDishNumber("\nВведите порядковый номер блюда, которое нужно изменить: ");
        int secsToCook = menuReader.getDishSecsToCook();
        restaurantRepository.getRestaurant().getDishesMenu().get(dishNumber-1).setSecToCook(secsToCook);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("Время приготовления успешно изменено!");
    }

    public void processInfoShowing() {
        AdminMenuPrinter adminMenuPrinter = new AdminMenuPrinter(restaurantRepository);
        adminMenuPrinter.printStats();
    }
}
