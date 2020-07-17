package com.company.desginpatterns.observer;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionsDisplay implements Observer {
    Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherData) {
            WeatherData wData = (WeatherData) obs;
            this.temperature = wData.getTemperature();
            this.humidity = wData.getHumidity();
            display();
        }
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " +
                + humidity + "% humidity");
    }

}
