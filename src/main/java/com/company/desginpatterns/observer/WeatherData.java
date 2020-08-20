package com.company.desginpatterns.observer;

import java.util.Observable;

public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {};

    public void measurementsChanged() {
        setChanged();       // 调用 notifyObserver之前需要调用setChanged，来标记状态以改变
        notifyObservers();  // 未传送数据对象，为pull的方式
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public static void main(String[] args) {
        WeatherData wd = new WeatherData();
        CurrentConditionsDisplay ccd = new CurrentConditionsDisplay(wd);
        ForecastDisplay fd = new ForecastDisplay(wd);
        wd.setMeasurements(87.6f, 50.6f, 23.5f);
    }
}
