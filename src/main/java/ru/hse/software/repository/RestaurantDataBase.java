package ru.hse.software.repository;

import lombok.Getter;
import ru.hse.software.authentication.AuthenticationEncryptor;
import ru.hse.software.exceptions.DataBaseSerializingException;
import ru.hse.software.exceptions.NoSuchAccountException;
import ru.hse.software.models.Account;
import ru.hse.software.models.Restaurant;
import ru.hse.software.orders.OrdersHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class RestaurantDataBase implements RestaurantRepository, Serializable {
    private ArrayList<Account> accounts;
    private final String path;
    @Getter private Restaurant restaurant = new Restaurant();
    @Getter OrdersHandler ordersHandler = new OrdersHandler();

    public RestaurantDataBase(String path) {
        this.path = path;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            RestaurantDataBase dataBase = (RestaurantDataBase) objectInputStream.readObject();
            accounts = dataBase.accounts;
            restaurant = dataBase.restaurant;
            ordersHandler = dataBase.ordersHandler;
            ordersHandler.start();
        } catch (FileNotFoundException ex) {
            System.out.println();
            System.out.println("В файле с данными о ресторане по указанному пути пока ничего нет, " +
                    "поэтому сейчас вы работаете с рестораном по умолчанию, в котором есть только аккаунт \n" +
                    "админа со стандартным логином и паролем.");
            accounts = new ArrayList<>();
            AuthenticationEncryptor authenticationEncryptor = new AuthenticationEncryptor();
            String hashedPass = authenticationEncryptor.encryptPass("admin");
            addAccount("admin", hashedPass, true);
            ordersHandler.start();
        } catch (Exception ex) {
            throw new DataBaseSerializingException("Не удалось прочитать базу данных о ресторане :(", ex);
        }
    }

    public boolean checkDoesAccountWithSuchLoginExists(String login) {
        for (Account account : accounts) {
            boolean isLoginCorrect = Objects.equals(login, account.getLogin());
            if (isLoginCorrect) {
                return true;
            }
        }
        return false;
    }

    public Account getAccount(String login, String hashedPass) {
        for (Account account : accounts) {
            boolean isLoginCorrect = Objects.equals(login, account.getLogin());
            boolean isPassCorrect = Objects.equals(hashedPass, account.getHashedPass());
            if (isLoginCorrect && isPassCorrect) {
                return account;
            }
        }
        throw new NoSuchAccountException("Аккаунт не найден :(", null);
    }

    public Account addAccount(String login, String hashedPass, boolean isAdmin) {
        Account account = new Account(login, hashedPass, isAdmin);
        accounts.add(account);
        try {
            saveRestaurantInfo();
        } catch (DataBaseSerializingException ex) {
            System.out.printf("Ошибка! %s\n", ex.getMessage());
        }
        return account;
    }

    public void saveRestaurantInfo() {
        try {
            Thread.sleep(1000);
            FileOutputStream outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (Exception ex) {
            //throw new DataBaseSerializingException("Не удалось сохранить данные о ресторане :(", ex);
        }
    }

}
