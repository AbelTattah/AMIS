package com.gp27.amis;

import com.gp27.amis.datastructures.HashMap;

public class TestHashMap {
    public static void main(String[] args) {
        // Test the HashMap implementation
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("key1", "value1");
        testMap.put("key2", "value2");
        
        // Test values() method
        for (String value : testMap.values()) {
            System.out.println("Value: " + value);
        }
    }
}
