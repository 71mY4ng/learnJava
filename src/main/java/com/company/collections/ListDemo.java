package com.company.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {
    
    public static void LinkedListDemo () {
        List<String> staff = new LinkedList<>();
        
        staff.add("Bob");
        staff.add("Amy");
        staff.add("Tim");

        Iterator iter = staff.iterator();

        for (String staffName:
        staff){
            System.out.println(staffName);
        }
    }
    public static void main(String[] args) {
        LinkedListDemo();
    }
}
