package ru.hse.software.controller;

import ru.hse.software.exceptions.DataBaseSerializingException;
import ru.hse.software.menu.MenuReader;
import ru.hse.software.menu.printers.DishesPrinter;
import ru.hse.software.menu.printers.VisitorMenuPrinter;
import ru.hse.software.models.Account;
import ru.hse.software.models.Dish;
import ru.hse.software.orders.FinishedOrder;
import ru.hse.software.orders.Order;
import ru.hse.software.orders.OrdersHandler;
import ru.hse.software.repository.RestaurantRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class VisitorHandler {
    RestaurantRepository restaurantRepository;
    MenuReader menuReader;
    VisitorMenuPrinter visitorMenuPrinter;
    DishesPrinter dishesPrinter;

    public VisitorHandler(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        menuReader = new MenuReader(restaurantRepository);
        this.visitorMenuPrinter = new VisitorMenuPrinter(restaurantRepository);
        dishesPrinter = new DishesPrinter(restaurantRepository);
    }

    public void processOrderCreating(Account account) {
        if (!dishesPrinter.printDishes()) {
            System.out.println("К сожалению, заказ сделать нельзя :(");
            return;
        }
        ArrayList<Dish> dishesToOrder = new ArrayList<>();
        do {
            int dishNum = menuReader.getDishNumber("\nВведите номер блюда, которое хотите добавить: ");
            Dish chosenDish = restaurantRepository.getRestaurant().getDishesMenu().get(dishNum - 1);
            if (chosenDish.getQuantity() > 0) {
                dishesToOrder.add(chosenDish);
                int quantity = chosenDish.getQuantity();
                restaurantRepository.getRestaurant().getDishesMenu().get(dishNum - 1).setQuantity(quantity - 1);
            } else {
                System.out.println("К сожалению, этого блюда нет в наличии :(");
            }
        } while (menuReader.askIfUserWantsAddOneMoreDish());
        if (dishesToOrder.isEmpty()) {
            return;
        }
        restaurantRepository.getOrdersHandler().createOrder(dishesToOrder, account);
        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        System.out.println("\nСпасибо за заказ! Можете посмотреть его статус в меню.");
    }

    public void processOrdersPrinting(Account account) {
        System.out.println();
        if (account.getFinishedOrders().isEmpty() && account.getNotFinishedOrders().isEmpty()) {
            System.out.println("У вас пока нет заказов.");
            return;
        }
        if (!account.getNotFinishedOrders().isEmpty()) {
            System.out.println("Еще не завершенные заказы:");
            visitorMenuPrinter.printOrdersFromList(account.getNotFinishedOrders());
        }
        if (!account.getFinishedOrders().isEmpty()) {
            System.out.println("Уже завершенные заказы:");
            visitorMenuPrinter.printFinishedOrdersFromList(account.getFinishedOrders());
        }
    }

    public void processAddingDishes(Account account) {
        System.out.println();
        ArrayList<Integer> ordersIds = new ArrayList<>();
        ArrayList<Order> orders = account.getNotFinishedOrders();
        if (!account.getNotFinishedOrders().isEmpty()) {
            System.out.println("Еще не завершенные заказы:");

            for (Order order : orders) {
                ordersIds.add(order.getOrderId());
            }
            visitorMenuPrinter.printOrdersFromList(orders);
        } else {
            System.out.println("У вас нет незавершенных заказов :(");
            return;
        }

        int orderNumber = menuReader.getOrderNumber("\nВведите номер заказа, куда хотите добавить блюдо: ",
                account);
        if (orderNumber == 0) {
            return;
        }

        dishesPrinter.printDishes();
        if (restaurantRepository.getRestaurant().getDishesMenu().isEmpty()) {
            return;
        }
        int dishNum = menuReader.getDishNumber("\nВведите номер блюда, которое хотите добавить: ");

        Order order;
        try {
            order = account.getNotFinishedOrders().get(orderNumber - 1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Этот заказ уже приготовился!");
            return;
        }

        if (ordersIds.get(orderNumber - 1) != order.getOrderId()) {
            System.out.println("Этот заказ уже приготовился!");
            return;
        }

        Dish chosenDish = restaurantRepository.getRestaurant().getDishesMenu().get(dishNum - 1);
        if (chosenDish.getQuantity() > 0) {
            order.getUncookedDishes().add(chosenDish);
            order.getAllDishes().add(chosenDish);
            order.setTotalPrice((int) (order.getTotalPrice() + chosenDish.getPrice()));
            int quantity = chosenDish.getQuantity();
            restaurantRepository.getRestaurant().getDishesMenu().get(dishNum - 1).setQuantity(quantity - 1);
            System.out.println("Блюдо успешно добавлено!");
        } else {
            System.out.println("К сожалению, этого блюда нет в наличии :(");
        }

        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
    }

    public void processOrderDeleting(Account account, OrdersHandler ordersHandler) {
        System.out.println();
        ArrayList<Integer> ordersIds = new ArrayList<>();
        ArrayList<Order> orders = account.getNotFinishedOrders();
        if (!account.getNotFinishedOrders().isEmpty()) {
            System.out.println("Еще не завершенные заказы:");
            for (Order order : orders) {
                ordersIds.add(order.getOrderId());
            }
            visitorMenuPrinter.printOrdersFromList(orders);
        } else {
            System.out.println("У вас нет незавершенных заказов :(");
            return;
        }

        int orderNumber = menuReader.getOrderNumber("\nВведите номер заказа, который хотите отменить: ",
                account);
        if (orderNumber == 0) {
            return;
        }

        Order order;
        try {
            order = account.getNotFinishedOrders().get(orderNumber - 1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Этот заказ уже приготовился!");
            return;
        }

        if (ordersIds.get(orderNumber - 1) != order.getOrderId()) {
            System.out.println("Этот заказ уже приготовился!");
            return;
        }

        account.getNotFinishedOrders().remove(order);
        ordersHandler.getOrdersInProcessing().remove(order);
        ordersHandler.getOrdersInCooking().remove(order);
        int setOrdersInCookAmount = ordersHandler.getOrdersInCookAmount();
        ordersHandler.setOrdersInCookAmount(setOrdersInCookAmount - 1);
        order.setDeleted(true);
        order.setActive(false);

        System.out.println("Заказ успешно отменен!");

        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
    }

    public void processOrderPaying(Account account) {
        System.out.println();
        boolean areUnpaidOrders = false;
        for (FinishedOrder order : account.getFinishedOrders()) {
            if (!order.isPaid()) {
                areUnpaidOrders = true;
                break;
            }
        }
        if (!areUnpaidOrders) {
            System.out.println("У вас нет неоплаченных приготовленных заказов!");
            return;
        }
        System.out.println("Ваши неоплаченные приготовленные заказы:");
        int k = 1;
        int id = 0;
        ArrayList<Integer> ordersIds = new ArrayList<>();
        for (FinishedOrder order : account.getFinishedOrders()) {
            if (!order.isPaid()) {
                visitorMenuPrinter.printFinishedOrder(order, k);
                ++k;
                ordersIds.add(id);
            }
            ++id;
        }

        int orderNum = menuReader.getFinishedOrderNumber("\nВведите номер закакза, который хотите оплатить: ",
                k-1);

        account.getFinishedOrders().get(ordersIds.get(orderNum - 1)).setPaid(true);
        double price = account.getFinishedOrders().get(ordersIds.get(orderNum - 1)).getTotalPrice();
        System.out.println("Заказ успешно оплачен!");
        double totalProfit = restaurantRepository.getRestaurant().getTotalProfit();
        restaurantRepository.getRestaurant().setTotalProfit(totalProfit + price);
        int ordersAmount = restaurantRepository.getRestaurant().getPaidOrdersAmount();
        restaurantRepository.getRestaurant().setPaidOrdersAmount(ordersAmount + 1);
        restaurantRepository.getRestaurant().setAverageProfit((totalProfit + price) / (ordersAmount + 1));

        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
    }

    public void processFeedbackMaking(Account account) {
        System.out.println();
        boolean areUnestimatedOrders = false;
        for (FinishedOrder order : account.getFinishedOrders()) {
            if (!order.isEstimated()) {
                areUnestimatedOrders = true;
                break;
            }
        }
        if (!areUnestimatedOrders) {
            System.out.println("У вас нет неоцененных приготовленных заказов!");
            return;
        }
        System.out.println("Ваши неоцененные приготовленные заказы:");
        int k = 1;
        int id = 0;
        ArrayList<Integer> ordersIds = new ArrayList<>();
        for (FinishedOrder order : account.getFinishedOrders()) {
            if (!order.isEstimated()) {
                visitorMenuPrinter.printFinishedOrder(order, k);
                ++k;
                ordersIds.add(id);
            }
            ++id;
        }

        int orderNum = menuReader.getFinishedOrderNumber("\nВведите номер закакза, который хотите оценить: ",
                k-1);

        int mark = menuReader.getMark();
        String feedback = menuReader.getFeedback();
        int ordersAmount = restaurantRepository.getRestaurant().getEstimatedOrdersAmount();
        restaurantRepository.getRestaurant().setEstimatedOrdersAmount(ordersAmount + 1);
        int ratingSum = restaurantRepository.getRestaurant().getRatingSum();
        restaurantRepository.getRestaurant().setRatingSum(ratingSum + mark);
        restaurantRepository.getRestaurant().setAverageRating((double) (ratingSum + mark) / (ordersAmount + 1));

        account.getFinishedOrders().get(ordersIds.get(orderNum - 1)).setEstimated(true);
        account.getFinishedOrders().get(ordersIds.get(orderNum - 1)).setFeedback(feedback);
        account.getFinishedOrders().get(ordersIds.get(orderNum - 1)).setMark(mark);
        System.out.println("Отзыв успешно оставлен!");

        try {
            restaurantRepository.saveRestaurantInfo();
        } catch (DataBaseSerializingException | FileNotFoundException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
    }
}
