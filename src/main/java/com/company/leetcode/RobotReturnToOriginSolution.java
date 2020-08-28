package com.company.leetcode;

import java.util.Optional;

public class RobotReturnToOriginSolution {

    enum Action {

        /** */
        R(new PositionXY(1, 0)),
        /** */
        L(new PositionXY(-1, 0)),
        /** */
        U(new PositionXY(0, 1)),
        /** */
        D(new PositionXY(0, -1)),
        /** */
        NOACTION(new PositionXY(0, 0)),
        ;

        private PositionXY change;

        Action(PositionXY change) {
            this.change = change;
        }

        public static Optional<Action> parse(String actionKey) {
            for (Action action : Action.values()) {
                if (action.name().equalsIgnoreCase(actionKey)) return Optional.of(action);
            }
            return Optional.empty();
        }
    }

    static class PositionXY {
        int x;
        int y;

        public PositionXY() {
            this.x = 0;
            this.y = 0;
        }

        public PositionXY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean atStartingPoint() {
            return (x == 0 && y == 0);
        }

        public void move(Action action) {
            this.x += action.change.x;
            this.y += action.change.y;
        }
    }


    public boolean judgeCircle(String moves) {

        final PositionXY robotPosition = new PositionXY();
        for (char move : moves.toCharArray()) {
            robotPosition.move(
                    Action.parse(String.valueOf(move))
                            .orElse(Action.NOACTION));
        }

        return robotPosition.atStartingPoint();
    }

    public static void main(String[] args) {

        final RobotReturnToOriginSolution a = new RobotReturnToOriginSolution();
        System.out.println(a.judgeCircle("LL"));
    }
}
