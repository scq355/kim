package com.kim.algorithm.greedy;

import java.util.Arrays;
import java.util.Comparator;

class Coin {
    public int value; // 面值
    public int weight; // 重量

    Coin(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }
}

public class CoinChange {

    // 方法实现贪心算法找到最小重量
    public static int minWeight(Coin[] coins, int Y) {
        // 按照面值与重量的比例排序
        Arrays.sort(coins, (c1, c2) -> Double.compare((double) c2.value / c2.weight, (double) c1.value / c1.weight));

        int totalValue = 0; // 当前支付的总面值
        int totalWeight = 0; // 当前总重量

        for (Coin coin : coins) {
            if (totalValue >= Y) {
                break; // 如果已达到目标面值，停止选择
            }
            // 选择当前银币
            totalValue += coin.value;
            totalWeight += coin.weight;
        }

        // 如果无法支付 Y，则返回 -1
        return totalValue >= Y ? totalWeight : -1;
    }

    // 主程序
    public static void main(String[] args) {
        Coin[] coins = {
                new Coin(1, 1),    // 面值 1， 重量 1
                new Coin(5, 3),    // 面值 5， 重量 3
                new Coin(10, 5),   // 面值 10， 重量 5
                new Coin(25, 7)    // 面值 25， 重量 7
        };

        int Y = 30; // 目标支付的面值
        int minTotalWeight = minWeight(coins, Y);

        if (minTotalWeight != -1) {
            System.out.println("Minimum weight to pay " + Y + " is: " + minTotalWeight);
        } else {
            System.out.println("Cannot pay the amount " + Y);
        }
    }
}

