package com.company.collections;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {
        Map<Integer, String> rbTreeMap = new HashMap<>(256);

        rbTreeMap.put(14, "14-fourteen");
        rbTreeMap.put(15, "15-fifteen");
        rbTreeMap.put(20, "20-twenty");
        rbTreeMap.put(21, "21-twenty-one");
        rbTreeMap.put(24, "24-twenty-four");
        rbTreeMap.put(13, "13-thirteen");
        rbTreeMap.put(16, "16-sixteen");
        rbTreeMap.put(30, "30-thirty");
    }
}
