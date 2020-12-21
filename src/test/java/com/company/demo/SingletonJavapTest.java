package com.company.demo;

public class SingletonJavapTest {

    private volatile static SingletonJavapTest instance;

    public static SingletonJavapTest getInstance() {
        if (instance == null) {
            synchronized (SingletonJavapTest.class) {
                if (instance == null)
                    instance = new SingletonJavapTest();
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        SingletonJavapTest.getInstance();
    }
}
