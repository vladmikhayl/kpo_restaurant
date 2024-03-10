package ru.hse.software.menu.printers;

import ru.hse.software.models.Dish;
import ru.hse.software.orders.FinishedOrder;
import ru.hse.software.orders.Order;
import ru.hse.software.repository.RestaurantRepository;

import java.util.ArrayList;

public class VisitorMenuPrinter implements MenuPrinter {
    RestaurantRepository restaurantDataBase;

    public VisitorMenuPrinter(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
    }

    public void printMenu() {
        System.out.print("""
                
                ВЫ МОЖЕТЕ:
                0) Выйти из аккаунта
                1) Просмотреть статус моих заказов
                2) Сделать новый заказ
                3) Добавить блюда в созданный заказ
                4) Отменить заказ
                5) Оплатить приготовленный заказ
                6) Оставить отзыв о завершенном заказе
                """);
    }

    public void printOrdersFromList(ArrayList<Order> orders) {
        int k = 1;
        for (Order order : orders) {
            System.out.printf("%d) Блюда: ", k);
            int dishes_printed_amount = 0;
            int dishes_amount = order.getAllDishes().size();
            for (Dish dish : order.getAllDishes()) {
                if (dishes_printed_amount + 1 == dishes_amount) {
                    System.out.printf("%s. ", dish.getName());
                } else {
                    System.out.printf("%s, ", dish.getName());
                }
                ++dishes_printed_amount;
            }
            System.out.printf("Статус: %s.\n", order.getStatus());
            ++k;
        }
    }

    public void printFinishedOrdersFromList(ArrayList<FinishedOrder> orders) {
        int k = 1;
        for (FinishedOrder order : orders) {
            System.out.printf("%d) Блюда: ", k);
            int dishes_printed_amount = 0;
            int dishes_amount = order.getCookedDishes().size();
            for (Dish dish : order.getCookedDishes()) {
                if (dishes_printed_amount + 1 == dishes_amount) {
                    System.out.printf("%s. ", dish.getName());
                } else {
                    System.out.printf("%s, ", dish.getName());
                }
                ++dishes_printed_amount;
            }
            System.out.print("Статус: Готов. ");
            if (order.isPaid()) {
                System.out.print("Оплачен. ");
            } else {
                System.out.print("Не оплачен. ");
            }
            if (order.isEstimated()) {
                System.out.println("Отзыв оставлен.");
            } else {
                System.out.println("Отзыв не оставлен.");
            }
            ++k;
        }
    }

    public void printFinishedOrder(FinishedOrder order, int k) {
        System.out.printf("%d) Блюда: ", k);
        int dishes_printed_amount = 0;
        int dishes_amount = order.getCookedDishes().size();
        for (Dish dish : order.getCookedDishes()) {
            if (dishes_printed_amount + 1 == dishes_amount) {
                System.out.printf("%s. ", dish.getName());
            } else {
                System.out.printf("%s, ", dish.getName());
            }
            ++dishes_printed_amount;
        }
        System.out.printf("К оплате: %d.\n", order.getTotalPrice());
    }
}
