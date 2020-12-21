package com.company.demo;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class CollectionTest {

    @Test
    public void atest() {

        final List<Object> objects = Collections.synchronizedList(new ArrayList<>());

        final CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
    }

    @Test
    public void hashMapTest() {

        final int h = "omg".hashCode();
        System.out.println(Long.toBinaryString(h));
        System.out.println(Long.toBinaryString(16));
        System.out.println(Long.toBinaryString(h >> 16));
        System.out.println(Long.toBinaryString(h >>> 16));
        System.out.println(Long.toBinaryString(h ^ (h >>> 16)));

    }

}
