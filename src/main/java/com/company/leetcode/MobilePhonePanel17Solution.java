package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 *
 * @author Tim
 * @date 2020年8月26日
 */
public class MobilePhonePanel17Solution {

    final Map<Character, String[]> panelMap = new HashMap<>(9);
    {
        panelMap.put('2', new String[] {"a", "b", "c"});
        panelMap.put('3', new String[] {"d", "e", "f"});
        panelMap.put('4', new String[] {"g", "h", "i"});
        panelMap.put('5', new String[] {"j", "k", "l"});
        panelMap.put('6', new String[] {"m", "n", "o"});
        panelMap.put('7', new String[] {"p", "q", "r", "s"});
        panelMap.put('8', new String[] {"t", "u", "v"});
        panelMap.put('9', new String[] {"w", "x", "y", "z"});
    }

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        for (char c : digits.toCharArray()) {
            if (result.isEmpty()) {
                result = Arrays.asList(panelMap.get(c));
            } else {
                List<String> cached = new ArrayList<>();
                for (String s : panelMap.get(c)) {
                    for (String item : result) {
                        cached.add(item + s);
                    }
                }
                result = new ArrayList<>(cached);
            }
        }

        return result;
    }


    public static void main(String[] args) {
        final List<String> strings = new MobilePhonePanel17Solution().letterCombinations("3456");

        strings.forEach(System.out::println);
    }
}
