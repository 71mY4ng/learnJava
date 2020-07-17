package com.company.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapDemo {

    public void hashMapIterTest () {
        // #1 高效的遍历方式
        Map map = new HashMap();
        Iterator iter = map.entrySet().iterator();  // 直接读取entry
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
        }

        // #2 低效的遍历方式
        Iterator iter2 = map.keySet().iterator();   // 取iterator对keys操作了一次
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = map.get(key);    // 使用key来对图中的index操作value, 对图进行了第二次操作
        }
    }
}
