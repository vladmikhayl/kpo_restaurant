package ru.hse.software.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
public class Restaurant implements Serializable {
    private final ArrayList<Dish> dishesMenu = new ArrayList<>();

    @Setter double totalProfit = 0;
    @Setter double averageProfit = 0;
    @Setter double averageRating = 0;
    @Setter int paidOrdersAmount = 0;
    @Setter int estimatedOrdersAmount = 0;
    @Setter int ratingSum = 0;
}
