package ru.hse.software.menu;

import ru.hse.software.models.Account;
import ru.hse.software.models.Dish;
import ru.hse.software.repository.RestaurantRepository;

import java.util.Objects;

public class MenuValidator {
    RestaurantRepository restaurantRepository;

    public MenuValidator(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    boolean checkIsDishNameCorrect(String input) {
        boolean isInputCorrect = true;
        for (Dish dish : restaurantRepository.getRestaurant().getDishesMenu()) {
            if (Objects.equals(dish.getName(), input)) {
                System.out.println("Блюдо с таким названием уже существует :(");
                isInputCorrect = false;
                break;
            }
        }
        return isInputCorrect;
    }

    boolean checkIsDishPriceCorrect(String input) {
        double price;
        try {
            price = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (price <= 0) {
            System.out.println("Цена должна быть положительной :(");
            return false;
        }
        return true;
    }

    boolean checkIsDishQuantityCorrect(String input) {
        int quantity;
        try {
            quantity = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (quantity < 0) {
            System.out.println("Количество не может быть отрицательным :(");
            return false;
        }
        return true;
    }

    boolean checkIsDishSecsToCookCorrect(String input) {
        int secs;
        try {
            secs = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (secs <= 0) {
            System.out.println("Количество секунд должно быть положительным :(");
            return false;
        }
        return true;
    }

    boolean checkIsDishNumberCorrect(String input) {
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (num > restaurantRepository.getRestaurant().getDishesMenu().size()) {
            System.out.println("Блюда с таким номером нет :(");
            return false;
        }
        if (num < 1) {
            System.out.println("Блюда с таким номером нет :(");
            return false;
        }
        return true;
    }

    boolean checkIsOrderNumberCorrect(String input, Account account) {
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (num > account.getNotFinishedOrders().size()) {
            System.out.println("Заказа с таким номером нет :(");
            return false;
        }
        if (num < 1) {
            System.out.println("Заказа с таким номером нет :(");
            return false;
        }
        return true;
    }

    boolean checkIsUnpaidOrderNumberCorrect(String input, int ordersAmount) {
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (num > ordersAmount) {
            System.out.println("Заказа с таким номером нет :(");
            return false;
        }
        if (num < 1) {
            System.out.println("Заказа с таким номером нет :(");
            return false;
        }
        return true;
    }

    boolean checkIsMarkCorrect(String input) {
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Введенное значение не является числом :(");
            return false;
        }
        if (num > 5) {
            System.out.println("Оценка должна быть от 1 до 5!");
            return false;
        }
        if (num < 1) {
            System.out.println("Оценка должна быть от 1 до 5!");
            return false;
        }
        return true;
    }
}
