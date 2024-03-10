package ru.hse.software.orders;

import lombok.Getter;
import lombok.Setter;
import ru.hse.software.models.Dish;

import java.io.Serializable;
import java.util.ArrayList;

public class FinishedOrder implements Serializable {
    @Getter ArrayList<Dish> cookedDishes;

    @Getter @Setter boolean isEstimated = false;
    @Getter @Setter boolean isPaid = false;
    @Getter @Setter int totalPrice;

    @Setter String feedback;
    @Setter int mark;

    public FinishedOrder(ArrayList<Dish> cookedDishes, int totalPrice) {
        this.cookedDishes = cookedDishes;
        this.totalPrice = totalPrice;
    }

}
