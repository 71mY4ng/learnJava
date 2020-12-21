package com.company.demo;

import org.junit.Test;

import java.util.logging.Logger;

public class LoggingDemo {

    public class HelloWorld {
        public void greet() {
            System.out.println("Hello world!");
        }
    }

    @Test
    public void LoggingProxyTest() {
        new HelloWorld().greet();
        HelloWorld hw = new HelloWorld() {
            public void greet() {
                super.greet();
                Logger.getGlobal().info("im the helloworld logging proxy");
            }
        };
        hw.greet();
    }
}
