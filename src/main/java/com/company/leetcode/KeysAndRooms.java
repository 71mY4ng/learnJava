package com.company.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/keys-and-rooms/comments/
 */
public class KeysAndRooms {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {

        boolean[] visited = new boolean[rooms.size()];

        // start from room 0
        return traversal(rooms, 0, visited);
    }

    boolean traversal(List<List<Integer>> rooms, int roomIndex, boolean[] visited) {

        // current room entered
        if (visited[roomIndex]) {

            return false;
        } else {

            visited[roomIndex] = true;
        }

        final List<Integer> keys = rooms.get(roomIndex);

        if (keys == null || keys.isEmpty()) {
            return allMatch(visited, true);
        }

        for (int key : keys) {
            if (!visited[key] && traversal(rooms, key, visited)) break;
        }

        return allMatch(visited, true);
    }

    static boolean allMatch(boolean[] arr, boolean value) {
        boolean r = value;
        for (boolean b : arr) {
            r &= b;
        }
        return r;
    }

    public static void main(String[] args) {

        List<List<Integer>> roomsAndKeys = new ArrayList<>();

        roomsAndKeys.add(0, Arrays.asList(1, 3));
        roomsAndKeys.add(1, Arrays.asList(3, 0, 1));
        roomsAndKeys.add(2, Arrays.asList(2, 1));
        roomsAndKeys.add(3, Arrays.asList(0));

        System.out.println(new KeysAndRooms().canVisitAllRooms(roomsAndKeys));
    }
}
