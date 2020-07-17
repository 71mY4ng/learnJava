package com.company.demo;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityHashMapDemo {
    public static void main(String[] args) {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
//        HashMap<String, Object> map = new HashMap<String, Object>();

        String fsStr = new String("xx");
        String secStr = new String("xx");
//        map.put(new String("xx"), "first");
        map.put(fsStr, "first");
//        map.put(new String("xx"), "second");
        map.put(secStr, "second");
//        map.put(fsStr, "second");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + "   ");
            System.out.println(entry.getValue());

        }
        System.out.println("identMap=" + map.containsKey(fsStr));
        System.out.println("identMap=" + map.get(fsStr));

//        System.out.println("identMap=" + map.containsKey("xx"));
//        System.out.println("identMap=" + map.get("xx"));

        System.out.println("identMap=" + map.containsKey(secStr));
        System.out.println("identMap=" + map.get(secStr));
    }
}
