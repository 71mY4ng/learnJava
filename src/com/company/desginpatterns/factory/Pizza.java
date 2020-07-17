package com.company.desginpatterns.factory;

import java.util.ArrayList;

public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    ArrayList toppings = new ArrayList();

    void bake() {
        System.out.println("bake for 25 ~ 30 minutes");
    }
    void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Toss dough " + dough);
        System.out.println("Adding sauce " + sauce);
        System.out.println("Adding toppings :");
        for (Object topping : toppings) {
            System.out.println(" " + topping);
        }
    }
    void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }
    void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }
}
