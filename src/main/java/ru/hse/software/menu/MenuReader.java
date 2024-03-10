package ru.hse.software.menu;

import ru.hse.software.models.Account;
import ru.hse.software.repository.RestaurantRepository;

import java.util.Objects;
import java.util.Scanner;

public class MenuReader {
    MenuParser menuParser = new MenuParser();
    Scanner console = new Scanner(System.in);
    RestaurantRepository restaurantRepository;
    MenuValidator menuValidator;

    public MenuReader(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        menuValidator = new MenuValidator(restaurantRepository);
    }

    public int readMenuItem(int minMenuItemNum, int maxMenuItemNum) {
        String input = console.nextLine();
        return menuParser.parseMenuItem(input, minMenuItemNum, maxMenuItemNum);
    }

    public String getDishName() {
        String input;
        do {
            System.out.print("\nВведите название блюда: ");
            input = console.nextLine();
        } while (!menuValidator.checkIsDishNameCorrect(input));
        return input;
    }

    public double getDishPrice() {
        String input;
        do {
            System.out.print("Введите стоимость блюда: ");
            input = console.nextLine();
        } while (!menuValidator.checkIsDishPriceCorrect(input));
        return Double.parseDouble(input);
    }

    public int getDishQuantity() {
        String input;
        do {
            System.out.print("Введите количество этого блюда: ");
            input = console.nextLine();
        } while (!menuValidator.checkIsDishQuantityCorrect(input));
        return Integer.parseInt(input);
    }

    public int getDishSecsToCook() {
        String input;
        do {
            System.out.print("Введите, сколько секунд нужно на приготовление блюда: ");
            input = console.nextLine();
        } while (!menuValidator.checkIsDishSecsToCookCorrect(input));
        return Integer.parseInt(input);
    }

    public int getDishNumber(String textToPrint) {
        String input;
        do {
            System.out.print(textToPrint);
            input = console.nextLine();
        } while (!menuValidator.checkIsDishNumberCorrect(input));
        return Integer.parseInt(input);
    }

    public boolean askIfUserWantsAddOneMoreDish() {
        System.out.print("Введите 1, если хотите добавить еще одно блюдо; введите что-нибудь другое иначе: ");
        String input = console.nextLine();
        return Objects.equals(input, "1");
    }

    public int getOrderNumber(String textToPrint, Account account) {
        String input;
        do {
            if (account.getNotFinishedOrders().isEmpty()) {
                System.out.println("Нет незавершенных заказов :(");
                return 0;
            }
            System.out.print(textToPrint);
            input = console.nextLine();
        } while (!menuValidator.checkIsOrderNumberCorrect(input, account));
        return Integer.parseInt(input);
    }

    public int getFinishedOrderNumber(String textToPrint, int ordersAmount) {
        String input;
        do {
            System.out.print(textToPrint);
            input = console.nextLine();
        } while (!menuValidator.checkIsUnpaidOrderNumberCorrect(input, ordersAmount));
        return Integer.parseInt(input);
    }

    public int getMark() {
        String input;
        do {
            System.out.print("Введите оценку от 1 до 5: ");
            input = console.nextLine();
        } while (!menuValidator.checkIsMarkCorrect(input));
        return Integer.parseInt(input);
    }

    public String getFeedback() {
        System.out.print("Введите ваш текстовый отзыв: ");
        return console.nextLine();
    }

}
