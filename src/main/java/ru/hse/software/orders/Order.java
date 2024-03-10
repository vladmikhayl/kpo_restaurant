package ru.hse.software.orders;

import lombok.Getter;
import lombok.Setter;
import ru.hse.software.models.Account;
import ru.hse.software.models.Dish;

import java.io.Serializable;
import java.util.ArrayList;

public class Order extends Thread implements Serializable, Comparable<Order> {
    static int lastId = 0;

    @Getter @Setter int totalPrice;
    int totalSecs = 0;
    int cookingSecs = 0;
    OrdersHandler ordersHandler;
    @Getter ArrayList<Dish> uncookedDishes;
    @Getter ArrayList<Dish> cookedDishes = new ArrayList<>();
    @Getter ArrayList<Dish> allDishes = new ArrayList<>();
    @Getter int orderId = lastId;
    Account client;
    @Getter String status = "В обработке";
    @Setter boolean isActive = true;
    @Setter boolean isDeleted = false;

    public void setClient(Account client) {
        this.client = client;
    }

    public Order(OrdersHandler ordersHandler, ArrayList<Dish> uncookedDishes) {
        this.ordersHandler = ordersHandler;
        this.uncookedDishes = uncookedDishes;
        allDishes.addAll(uncookedDishes);
        for (Dish dish : uncookedDishes) {
            totalPrice += (int) dish.getPrice();
        }
        ++lastId;
    }

    @Override
    public void run() {
        status = "Готовится";
        while (isActive) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Во время обработки заказа возникла ошибка :(");
            }
            for (Dish dish : uncookedDishes) {
                totalSecs += dish.getSecToCook();
                cookedDishes.add(dish);
            }
            uncookedDishes.clear();
            if (totalSecs - cookingSecs == 0) {
                break;
            }
            for (int i = 0; i < totalSecs - cookingSecs; ++i) {
                if (!isActive) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                    ++cookingSecs;
                } catch (InterruptedException e) {
                    System.out.println("Во время обработки заказа возникла ошибка :(");
                    break;
                }
            }

        }

        if (!isDeleted) {
            client.getNotFinishedOrders().remove(this);
            FinishedOrder finishedOrder = new FinishedOrder(allDishes, totalPrice);
            client.getFinishedOrders().add(finishedOrder);
            ordersHandler.getOrdersInProcessing().remove(this);
            ordersHandler.getOrdersInCooking().remove(this);
            int setOrdersInCookAmount = ordersHandler.getOrdersInCookAmount();
            ordersHandler.setOrdersInCookAmount(setOrdersInCookAmount - 1);
        }
    }

    @Override
    public int compareTo(Order o) {
        if (this.totalPrice > o.totalPrice) {
            return 1;
        } else if (this.totalPrice < o.totalPrice) {
            return -1;
        }
        return 0;
    }
}
