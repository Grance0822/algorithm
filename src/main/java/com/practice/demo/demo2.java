package com.practice.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: weiwenbo
 * @Date: 2023/8/23
 * 概述：
 */
public class FindUniqueValues {


    public static void main(String[] args) {
        String filePath = "E:\\0820.txt";
        Map<String, Integer> valueCountMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                valueCountMap.put(line, valueCountMap.getOrDefault(line, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Unique values that appear only once:");
        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
            }
        }
    }
}


