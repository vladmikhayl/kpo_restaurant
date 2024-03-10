package ru.hse.software.authentication;

import ru.hse.software.exceptions.ValidationException;
import java.util.Objects;
import java.util.Scanner;

public class AuthenticationReader {
    Scanner console = new Scanner(System.in);
    AuthenticationEncryptor authenticationEncryptor = new AuthenticationEncryptor();
    AuthenticationValidator authenticationValidator;

    public AuthenticationReader(AuthenticationValidator authenticationValidator) {
        this.authenticationValidator = authenticationValidator;
    }

    public String getLoginDuringAuthorization() {
        System.out.print("\nВведи свой логин: ");
        return console.nextLine();
    }

    public String getHashedPassDuringAuthorization() {
        System.out.print("Введи свой пароль: ");
        String pass = console.nextLine();
        return authenticationEncryptor.encryptPass(pass);
    }

    public String getLoginDuringRegistration() {
        String login;
        while (true) {
            System.out.print("\nПридумай себе логин: ");
            login = console.nextLine();
            try {
                authenticationValidator.validateLogin(login);
            } catch (ValidationException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
                continue;
            }
            break;
        }
        return login;
    }

    public String getHashedPassDuringRegistration() {
        String pass;
        while (true) {
            System.out.print("Придумай себе пароль: ");
            pass = console.nextLine();
            try {
                authenticationValidator.validatePass(pass);
            } catch (ValidationException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
                continue;
            }
            break;
        }
        return authenticationEncryptor.encryptPass(pass);
    }

    public boolean askIfUserWantsToContinue() {
        System.out.print("Введи 1, чтобы повторить попытку; введи что-то другое, чтобы вернуться обратно в меню: ");
        String input = console.nextLine();
        return Objects.equals(input, "1");
    }
}
