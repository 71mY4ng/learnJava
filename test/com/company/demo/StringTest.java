package com.company.demo;

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
}
