package com.company.desginpatterns.observer;

import java.io.BufferedInputStream;
import java.util.Observable;
import java.util.Observer;

public class ForecastDisplay implements Observer {
    Observable observable;

    private float currentPressure = 29.92f;
    private float lastPressure;

    public ForecastDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData wData = (WeatherData) o;
            lastPressure = currentPressure;
            currentPressure = wData.getPressure();
            display();
        }
    }

    public void display() {
        System.out.println("ForecastDisplay{" +
                "currentPressure=" + currentPressure +
                ", lastPressure=" + lastPressure +
                '}');
    }
}
