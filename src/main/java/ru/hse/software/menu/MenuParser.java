package ru.hse.software.menu;

import ru.hse.software.exceptions.MenuParsingException;

public class MenuParser {
    public int parseMenuItem(String input, int minMenuItemNum, int maxMenuItemNum) {
        int menuItem;

        try {
            menuItem = Integer.parseInt(input);
        } catch (Exception ex) {
            throw new MenuParsingException("Введенное значение не является числом :(", ex);
        }

        boolean isInputInRequiredInterval = menuItem >= minMenuItemNum && menuItem <= maxMenuItemNum;

        if (!isInputInRequiredInterval) {
            throw new MenuParsingException("Такого пункта меню нет :(", null);
        }

        return menuItem;
    }


}
