package com.company.demo;

import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

class LengthComparator implements Comparator<String>
{
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }

}

public class LambdaDemo {

    @Test
    public void normalTest() {
        String[] strings = new String[] {"Mercury", "Mars", "Earth", "Venus", "Jupiter", "Saturn", "Uranus", "Neptune"};
        Arrays.sort(strings, new LengthComparator());
        System.out.println(Arrays.toString(strings));
    }
    @Test
    public void basicTest() {

        Comparator<String> stringComparator = (String first, String second) -> first.length() - second.length();
        String[] strings = new String[] {"Mercury", "Mars", "Earth", "Venus", "Jupiter", "Saturn", "Uranus", "Neptune"};
        Arrays.sort(strings, stringComparator);
        System.out.println(Arrays.toString(strings));


    }

    @Test
    public void actionListenerLambdaTest () {
//        Timer t = new Timer (1000, System.out::println);    // functional ref
        Timer t = new Timer(1000, event -> System.out.println("Time is " + new Date()));
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }

    @Test
    public void smallTest() {
        new Thread( () -> System.out.println("Lambda thread print")).start();
    }

}
