package com.company.desginpatterns.factory;

public abstract class PizzaStore {

    public void orderPizza(String pizzaType) {

        Pizza pizza = createPizza(pizzaType);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    protected abstract Pizza createPizza(String pizzaType);
}
