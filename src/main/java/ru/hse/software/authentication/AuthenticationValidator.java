package ru.hse.software.authentication;

import ru.hse.software.exceptions.ValidationException;
import ru.hse.software.repository.RestaurantRepository;
import java.util.Objects;

public class AuthenticationValidator {
    RestaurantRepository restaurantDataBase;

    public AuthenticationValidator(RestaurantRepository restaurantDataBase) {
        this.restaurantDataBase = restaurantDataBase;
    }

    public void validateLogin(String login) {
        if (Objects.equals(login, "")) {
            throw new ValidationException("Логин не может быть пустым :(", null);
        }
        if (restaurantDataBase.checkDoesAccountWithSuchLoginExists(login)) {
            throw new ValidationException("Этот логин уже занят :(", null);
        }
    }

    public void validatePass(String pass) {
        if (pass.length() < 4) {
            throw new ValidationException("Пароль должен иметь длину хотя бы 4 символа :(", null);
        }
        if (pass.length() > 20) {
            throw new ValidationException("Пароль не может быть длиннее 20 символов :(", null);
        }
    }
}
