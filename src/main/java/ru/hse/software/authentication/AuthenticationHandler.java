package ru.hse.software.authentication;

import ru.hse.software.exceptions.EncryptionException;
import ru.hse.software.exceptions.NoSuchAccountException;
import ru.hse.software.repository.RestaurantRepository;
import ru.hse.software.models.Account;

public class AuthenticationHandler {
    AuthenticationReader authenticationReader;
    RestaurantRepository restaurantDataBase;

    public AuthenticationHandler(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
        AuthenticationValidator authenticationValidator = new AuthenticationValidator(restaurantDataBase);
        authenticationReader = new AuthenticationReader(authenticationValidator);
    }

    public Account processLogIn() {
        String login;
        String hashedPass;
        Account account;
        do {
            login = authenticationReader.getLoginDuringAuthorization();

            try {
                hashedPass = authenticationReader.getHashedPassDuringAuthorization();
            } catch (EncryptionException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
                if (authenticationReader.askIfUserWantsToContinue()) {
                    continue;
                } else {
                    return null;
                }
            }

            try {
                account = restaurantDataBase.getAccount(login, hashedPass);
            } catch (NoSuchAccountException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
                if (authenticationReader.askIfUserWantsToContinue()) {
                    continue;
                } else {
                    return null;
                }
            }

            break;
        } while (true);

        return account;
    }

    public Account processRegistration() {
        String login;
        String hashedPass;
        Account account;

        do {
            login = authenticationReader.getLoginDuringRegistration();

            try {
                hashedPass = authenticationReader.getHashedPassDuringRegistration();
            } catch (EncryptionException ex) {
                System.out.printf("Ошибка! %s\n", ex.getMessage());
                continue;
            }

            account = restaurantDataBase.addAccount(login, hashedPass, false);

            break;
        } while (true);

        return account;
    }
}
