package ru.hse.software.menu;

import ru.hse.software.exceptions.MenuParsingException;
import ru.hse.software.menu.printers.MenuPrinter;
import ru.hse.software.repository.RestaurantRepository;

public class Menu {
    private final MenuPrinter printer;
    private final int minMenuItemNum;
    private final int maxMenuItemNum;
    RestaurantRepository restaurantRepository;

    public Menu(MenuPrinter printer, int minMenuItemNum, int maxMenuItemNum, RestaurantRepository restaurantRepository) {
        this.printer = printer;
        this.minMenuItemNum = minMenuItemNum;
        this.maxMenuItemNum = maxMenuItemNum;
        this.restaurantRepository = restaurantRepository;
    }

    public void printMenu() {
        printer.printMenu();
    }

    public int selectMenuItem() {
        MenuReader menuReader = new MenuReader(restaurantRepository);
        int menuItem = 0;
        boolean isMenuItemCorrect = false;

        while (!isMenuItemCorrect) {
            System.out.printf("Введи пункт меню (от %d до %d): ", minMenuItemNum, maxMenuItemNum);
            try {
                menuItem = menuReader.readMenuItem(minMenuItemNum, maxMenuItemNum);
                isMenuItemCorrect = true;
            } catch (MenuParsingException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
            }
        }

        return menuItem;
    }
}
