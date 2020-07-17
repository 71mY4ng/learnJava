package com.company.demo;

import com.company.modal.Employee;
import org.junit.Test;

public class FunctionTest {

    @Test
    public void swapTest() {
        Employee empA = new Employee();
        Employee empB = new Employee();
        swap(empA, empB);
        System.out.println(empA.toString());
        System.out.println(empB.toString());
    }

    private static void swap(Employee x, Employee y) {
        Employee temp = x;
        x = y;
        y = temp;
    }

    @Test
    public void statusTest() {

    }
}
