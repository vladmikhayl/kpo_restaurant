package ru.hse.software.menu.printers;

import ru.hse.software.models.Dish;
import ru.hse.software.repository.RestaurantRepository;

import java.util.ArrayList;

public class DishesPrinter {
    RestaurantRepository restaurantDataBase;

    public DishesPrinter(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
    }

    public boolean printDishes() {
        ArrayList<Dish> dishes = restaurantDataBase.getRestaurant().getDishesMenu();
        if (dishes.isEmpty()) {
            System.out.println("\nСейчас в меню нет ни одного блюда.");
            return false;
        }
        System.out.println("\nСЕЙЧАС В МЕНЮ ЕСТЬ СЛЕДУЮЩИЕ БЛЮДА:");
        int k = 1;
        for (Dish dish : dishes) {
            System.out.printf("%d) Блюдо %s. Цена: %.2f. Количество: %d. Секунд на готовку: %d\n",
                    k++, dish.getName(), dish.getPrice(), dish.getQuantity(), dish.getSecToCook());
        }
        return true;
    }
}
