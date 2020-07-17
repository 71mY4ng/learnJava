package com.company.desginpatterns.factory;

import java.util.concurrent.ConcurrentHashMap;

public class ChessePizza extends Pizza {


    public ChessePizza() {
        name = "Sauce and Chesse Pizza";
        dough = "Thin Crust dough";
        sauce = "Marinara Sauce";

        toppings.add("Grated Reggiano Chesse");
    }

}
