package com.company.desginpatterns.factory;

public class NYStylePizzaStore extends PizzaStore{

    @Override
    protected Pizza createPizza(String pizzaType) {
        switch (pizzaType){
            case "chessePizza":
                return new ChessePizza();
            case "VegglePizza":
                return new VegglePizza();
            default:
                break;
        }

        return null;
    }
}
