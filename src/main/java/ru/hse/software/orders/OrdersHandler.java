package ru.hse.software.orders;

import lombok.Getter;
import lombok.Setter;
import ru.hse.software.models.Account;
import ru.hse.software.models.Dish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class OrdersHandler extends Thread implements Serializable {
    @Getter private ArrayList<Order> ordersInProcessing = new ArrayList<>();
    @Getter private ArrayList<Order> ordersInCooking = new ArrayList<>();
    @Setter @Getter private int ordersInCookAmount = 0;
    @Setter boolean isActive = true;

    public void createOrder(ArrayList<Dish> dishesToOrder, Account account) {
        Order order = new Order(this, dishesToOrder);
        order.setClient(account);
        ordersInProcessing.add(order);
        account.getNotFinishedOrders().add(order);
    }

    @Override
    public void run() {
        for (Order order : ordersInCooking) {
            order.setActive(true);
            order.start();
        }

        while (isActive) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Во время обработки заказа возникла ошибка :(");
            }

            if (ordersInProcessing.isEmpty() || ordersInCookAmount >= 3) {
                continue;
            }

            int maxTotalPrice = Collections.max(ordersInProcessing).totalPrice;
            for (Order order : ordersInProcessing) {
                if (order.totalPrice == maxTotalPrice) {
                    order.start();
                    ordersInProcessing.remove(order);
                    ordersInCooking.add(order);
                    ++ordersInCookAmount;
                    break;
                }
            }

        }
    }
}
