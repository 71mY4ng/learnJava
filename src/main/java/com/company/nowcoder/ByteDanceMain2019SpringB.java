package com.company.nowcoder;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;


/**
 * not pass
 */
public class ByteDanceMain2019SpringB {

    static class SolverB {

        int n;
        int d;
        List<Integer> locs;

        public SolverB(int n, int d, List<String> loc) {
            this.n = n;
            this.d = d;
            this.locs = loc.stream()
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toList());
        }

        int solCount = 0;
        public void solve() {

            dfs(locs.get(0), 3, 0);

            out.println(solCount);
        }

        private void dfs(int startPoint, int spyRemain, int index) {
            if (spyRemain == 0) {
                this.solCount++;
                return;
            }

            for (int i = index; i < locs.size(); i++) {
                if (startPoint + d >= locs.get(i)) {
                    dfs(startPoint, spyRemain - 1, i + 1);
                }
            }
        }

    }

    public static void main(String[] argv) {

        try (Scanner sc = new Scanner(System.in)) {

//            List<List<Object>> args = new ArrayList<>();
//            String first = sc.nextLine();
//            args.add(Arrays.stream(first.split(" "))
//                    .mapToInt(Integer::valueOf)
//                    .boxed()
//                    .collect(Collectors.toList()));

            int n = sc.nextInt();
            int d = sc.nextInt();

            sc.useDelimiter("\n");
            sc.nextLine();

            List<String> loc = new ArrayList<>(Arrays.asList(sc.nextLine().split(" ")));

            new SolverB(n, d, loc).solve();
        }
    }
}
