package com.practice.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: weiwenbo
 * @Date: 2023/8/22
 * 概述：
 */
public class demo1 {
    /**
     * leetCode 2830:  排序+动态规划
     * 给你一个整数 n 表示数轴上的房屋数量，编号从 0 到 n - 1 。
     * 另给你一个二维整数数组 offers ，其中 offers[i] = [starti, endi, goldi] 表示第 i 个买家想要以 goldi 枚金币的价格购买从 starti 到 endi 的所有房屋。
     * 作为一名销售，你需要有策略地选择并销售房屋使自己的收入最大化。
     * 返回你可以赚取的金币的最大数目。
     * 注意 同一所房屋不能卖给不同的买家，并且允许保留一些房屋不进行出售。
     * 示例 1：
     * 输入：n = 5, offers = [[0,0,1],[0,2,2],[1,3,2]]
     * 输出：3
     * 解释：
     * 有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
     * 将位于 [0,0] 范围内的房屋以 1 金币的价格出售给第 1 位买家，并将位于 [1,3] 范围内的房屋以 2 金币的价格出售给第 3 位买家。
     * 可以证明我们最多只能获得 3 枚金币。
     * 示例 2：
     * 输入：n = 5, offers = [[0,0,1],[0,2,10],[1,3,2]]
     * 输出：10
     * 解释：有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
     * 将位于 [0,2] 范围内的房屋以 10 金币的价格出售给第 2 位买家。
     * 可以证明我们最多只能获得 10 枚金币。
     * 提示：
     * 1 <= n <= 105
     * 1 <= offers.length <= 105
     * offers[i].length == 3
     * 0 <= starti <= endi <= n - 1
     * 1 <= goldi <= 103
     */
    //参考答案
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        // dp数组的含义为出售从0到i的房子，可以获得的最大收入
        int[] dp = new int[n];
        Map<Integer, List<Integer>> map = new HashMap<>();
        // map对应end为key时，offer的下标。
        for (int i = 0; i < offers.size(); i++) {
            int end = offers.get(i).get(1);
            if (!map.containsKey(end)) {
                map.put(end, new ArrayList<Integer>());
            }
            map.get(end).add(i);
        }
        // 初始化
        // 如果没有end为0的offer，0-0最大收入为0；
        // 如果有end为0的offer，找出最大收入赋值dp[0] (但是得到最大收入不一定需要end为0的offer！)
        dp[0] = 0;
        if (map.containsKey(0)) {
            for (int x : map.get(0)) {
                dp[0] = Math.max(dp[0], offers.get(x).get(2));
            }
        }
        // 从1到n-1 遍历dp数组
        for (int i = 1; i < n; i++) {
            // 如果不卖下标为i的房子
            dp[i] = dp[i - 1];

            // 如果卖下标为i的房子，找出所有end为i的的offer的start, 最大收入为dp[start - 1] + gold
            // map中没有key为i的房子说明没有end为i的offer
            if (map.containsKey(i)) {
                for (int j = 0; j < map.get(i).size(); j++) {
                    int start = offers.get(map.get(i).get(j)).get(0);
                    int gold = offers.get(map.get(i).get(j)).get(2);
                    // start = 0 代表房子从0到i只卖给了这个offer, 没有卖给end为0的offer
                    int income = start == 0 ? gold : dp[start - 1] + gold;
                    // 找最大值
                    dp[i] = Math.max(dp[i], income);
                }
            }
        }
        return dp[n - 1];
    }


}
