package ru.hse.software.models;

import lombok.Getter;
import ru.hse.software.orders.FinishedOrder;
import ru.hse.software.orders.Order;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
public class Account implements Serializable {
    private final String login;
    private final String hashedPass;
    private final boolean isAdmin;


    ArrayList<Order> notFinishedOrders = new ArrayList<>();
    ArrayList<FinishedOrder> finishedOrders = new ArrayList<>();

    public Account(String login, String hashedPass, boolean isAdmin) {
        this.login = login;
        this.hashedPass = hashedPass;
        this.isAdmin = isAdmin;
    }

}
