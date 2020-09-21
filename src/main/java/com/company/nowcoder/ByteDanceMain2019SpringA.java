package com.company.nowcoder;

import java.util.*;

import static java.lang.System.out;

/**
 * not pass
 */
public class ByteDanceMain2019SpringA {

    static class SolverA {

        public void solve(String s) {

            int wordLen = s.length();
            char[] chars = s.toCharArray();

            LinkedList<Character> cList = new LinkedList<>();
            for (char c: chars) {
                cList.add(c);
            }
            Stack<Character> stack = new Stack<>();


            Iterator<Character> iter = cList.iterator();
            while (iter.hasNext()) {
                char cur = iter.next();
                char curNext = iter.next();

                if (cur == curNext) {
                    stack.push(cur);
                    stack.push(curNext);
                }
            }

            for (int j = 0; j < wordLen; j++) {

                if (j + 1 < wordLen && chars[j] == chars[j + 1]) {

                    // AAA -> _↓AA
                    if (j + 2 < wordLen && chars[j] == chars[j + 2]) {
                        chars[j] = ' ';
                        continue;
                    }

                    // AABB -> AAB_↓
                    if (j + 3 < wordLen && chars[j + 2] == chars[j + 3]) {
                        chars[j + 2] = ' ';
                        j += 3;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                if (c != ' ')
                    sb.append(c);
            }

            out.print(sb.toString());
        }
    }

    public static void main(String[] argv) {

        try (Scanner sc = new Scanner(System.in)) {

            int n = sc.nextInt();
            sc.useDelimiter("\n");
            sc.nextLine();

            final SolverA solverA = new SolverA();
            while (sc.hasNext()) {
                String inputStr = sc.nextLine();

                solverA.solve(inputStr);

                if (sc.hasNext()) out.println();
            }

        }
    }

}
