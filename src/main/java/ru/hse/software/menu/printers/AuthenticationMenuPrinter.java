package ru.hse.software.menu.printers;

public class AuthenticationMenuPrinter implements MenuPrinter {
    public void printMenu() {
        System.out.print("""
                
                Привет! Чтобы начать работать с приложением, нужно войти в аккаунт.
                ВЫ МОЖЕТЕ:
                0) Выйти из приложения
                1) Войти в аккаунт
                2) Зарегистрироваться
                """);
    }
}
