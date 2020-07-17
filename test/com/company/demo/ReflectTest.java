package com.company.demo;

import com.company.modal.Employee;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

public class ReflectTest {

    @Test
    public void classNameTest() {
        Class cl1 = int.class;
        Date d = new Date();
        Class<Date> cl2 = (Class<Date>) d.getClass();

        System.out.println(cl1.getName());
        System.out.println(cl2.getName());
        System.out.println(Double[].class.getName());
        System.out.println(Long[].class.getName());
        System.out.println(int[].class.getName());
        System.out.println(Integer[].class.getName());

    }

    @Test
    public void newInstanceTest() {
        String s = "java.util.Date";
        Object m = null;
        try {
            m = Class.forName(s).newInstance();
            System.out.println(m.getClass().getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Constructor<Employee> empConstructor = Employee.class.getConstructor(int.class, String.class, double.class);
            Employee gary = empConstructor.newInstance(1, "Gary", 100.0);
            Method mSetId = Employee.class.getMethod("setId", int.class);
            System.out.println(gary);
            mSetId.invoke(gary, 2);
            System.out.println(Arrays.toString(Employee.class.getConstructors()));
            System.out.println(gary);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
