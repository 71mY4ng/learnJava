package com.company.leetcode;


/**
 * https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/submissions/
 * 有限向量机
 *
 * 可读较高的解法
 * https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/solution/biao-shi-shu-zhi-de-zi-fu-chuan-by-leetcode-soluti/
 */
public class IsNumber {

    public boolean isNumber(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        s = s.trim();

        boolean hasNum = false;
        boolean hasDot = false;
        boolean hasE = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= '0' && c <= '9') {
                hasNum = true;
            } else if (c == '.' && !hasE && !hasDot) {
                hasDot = true;
            } else if ((c == 'e' || c == 'E') && hasNum && !hasE) {
                hasE = true;
                hasNum = false;
            } else if ((c == '-' || c == '+')
                    && (i == 0 || s.charAt(i - 1) == 'e' || s.charAt(i - 1) == 'E')) {
                // ignore
            } else {
                return false;
            }
        }

        return hasNum;
    }

    public static void main(String[] args) {

        String[] trueArray = new String[]{"3.", "  1", "1  ", "-0.1", "0", "11", "1E-15", "-10E-10"};
        String[] falseArray = new String[]{".", "a", "", "   ", "E", "-10E-1.0", "12e+5.4", "1.2.3", "12e", "1a3.14", "+-15"};
        final IsNumber isNumber = new IsNumber();

        for (String s : trueArray) {
            if (!isNumber.isNumber(s)) {

                System.out.println(s);
            }
        }

        for (String s : falseArray) {
            if (isNumber.isNumber(s)) {

                System.out.println(s);
            }
        }
    }
}
