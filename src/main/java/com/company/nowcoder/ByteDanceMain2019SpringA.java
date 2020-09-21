package com.company.nowcoder;

import java.util.*;

import static java.lang.System.out;

/**
 * not pass
 */
public class ByteDanceMain2019SpringA {

    static class SolverA {

        public void solveRegex(String s) {

        }

        public void solveSM(String s) {

        }

        public void solve(String s) {

            char[] chars = s.toCharArray();

            LinkedList<Character> q = new LinkedList<>();

            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                q.offer(c);

                if (q.size() == 4) {
                    if (q.get(0).equals(q.get(1)) && q.get(0).equals(q.get(2))) {
                        // AAA -> AA
                        q.remove(2);
                    } else if (q.get(1).equals(q.get(2)) && q.get(2).equals(q.get(3))) {
                        // AAA -> AA
                        q.removeLast();
                    } else if (q.get(0).equals(q.get(1)) && q.get(2).equals(q.get(3))) {
                        // AABB -> AAB
                        q.removeLast();
                    } else {
                        sb.append(q.poll());
                    }
                }
            }

            for (Character c : q) {
                sb.append(c);
            }

            out.println(sb.toString());

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
