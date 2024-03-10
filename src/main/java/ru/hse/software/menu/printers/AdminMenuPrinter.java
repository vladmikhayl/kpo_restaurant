package ru.hse.software.menu.printers;

import ru.hse.software.repository.RestaurantRepository;

public class AdminMenuPrinter implements MenuPrinter {
    RestaurantRepository restaurantDataBase;
    DishesPrinter dishesPrinter;

    public AdminMenuPrinter(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
        dishesPrinter = new DishesPrinter(restaurantDataBase);
    }

    public void printMenu() {
        dishesPrinter.printDishes();
        System.out.print("""
                ВЫ МОЖЕТЕ:
                0) Выйти из аккаунта
                1) Добавить блюдо
                2) Удалить блюдо
                3) Изменить цену блюда
                4) Изменить количество блюда
                5) Изменить время приготовления блюда
                6) Просмотреть статистику
                """);
    }

    public void printStats() {
        System.out.printf("""
                
                СТАТИСТИКА О РЕСТОРАНЕ:
                Общая прибыль = %f
                Средняя прибыль за заказ = %f
                Средняя оценка за заказ = %f
                """,
                restaurantDataBase.getRestaurant().getTotalProfit(),
                restaurantDataBase.getRestaurant().getAverageProfit(),
                restaurantDataBase.getRestaurant().getAverageRating());

    }

}
