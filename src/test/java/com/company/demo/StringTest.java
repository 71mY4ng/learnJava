package com.company.demo;

import org.junit.Test;

public class StringTest {

    public static void main(String[] args) {


        String ab = "通话";

        String ar = "重地";

        String formatString = "hashCode(ab) : %d | hashCode(ar) : %d";
        // "通话" 和 "重地" 虽然 hashCode 相同，但equals 结果为不等, 说明 String::equals 包含了内容比较
        // 详细可以看 String::equals 的实现
        final String formatted = String.format(formatString, ab.hashCode(), ar.hashCode());
        System.out.println(formatted);
        System.out.println(ab.equals(ar));

        final StringBuffer reverse = new StringBuffer(formatted).reverse();
        System.out.println(reverse);

    }

    @Test
    public void testStringEquals() {

        String str1 = "HelloFlyapi";
        String str2 = "HelloFlyapi";
        String str3 = new String("HelloFlyapi");
        String str4 = "Hello";
        String str5 = "Flyapi";
        String str6 = "Hello" + "Flyapi";
        String str7 = str4 + str5;

        System.out.println("str1 == str2 result: " + (str1 == str2));

        System.out.println("str1 == str3 result: " + (str1 == str3));

        System.out.println("str1 == str6 result: " + (str1 == str6));

        System.out.println("str1 == str7 result: " + (str1 == str7));

        System.out.println("str1 == str7.intern() result: " + (str1 == str7.intern()));

        System.out.println("str3 == str3.intern() result: " + (str3 == str3.intern()));
    }

    @Test
    public void booleanEqualityTest() {
        System.out.println(new Boolean(true) == new Boolean(true));;
    }
}
