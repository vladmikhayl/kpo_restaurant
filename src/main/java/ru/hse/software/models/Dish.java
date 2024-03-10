package ru.hse.software.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Dish implements Serializable {
    String name;
    double price;
    int quantity;
    int secToCook;

    public Dish(String name, double price, int quantity, int secToCook) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.secToCook = secToCook;
    }

}
