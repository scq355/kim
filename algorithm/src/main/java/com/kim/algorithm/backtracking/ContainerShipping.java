package com.kim.algorithm.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContainerShipping {

    private List<List<List<Integer>>> results; // 存储所有方案
    private int[] containers; // 集装箱重量
    private int[] weightLimits; // 每艘船的载重限制

    public ContainerShipping(int[] containers, int[] weightLimits) {
        this.containers = containers;
        this.weightLimits = weightLimits;
        this.results = new ArrayList<>();
    }

    public List<List<List<Integer>>> findCombinations() {
        backtrack(0, new ArrayList<>(), new ArrayList<>(), 0, 0);
        return results;
    }

    private void backtrack(int index, List<Integer> ship1, List<Integer> ship2, int weight1, int weight2) {
        // 如果所有集装箱都已处理，将当前组合添加到结果中
        if (index == containers.length) {
            results.add(Arrays.asList(new ArrayList<>(ship1), new ArrayList<>(ship2)));
            return;
        }

        // 尝试将当前集装箱放入船1
        if (weight1 + containers[index] <= weightLimits[0]) {
            ship1.add(containers[index]);
            backtrack(index + 1, ship1, ship2, weight1 + containers[index], weight2);
            ship1.remove(ship1.size() - 1); // 撤销选择
        }

        // 尝试将当前集装箱放入船2
        if (weight2 + containers[index] <= weightLimits[1]) {
            ship2.add(containers[index]);
            backtrack(index + 1, ship1, ship2, weight1, weight2 + containers[index]);
            ship2.remove(ship2.size() - 1); // 撤销选择
        }

        // 尝试不放入任何船
        backtrack(index + 1, ship1, ship2, weight1, weight2);
    }

    public static void main(String[] args) {
        int[] containers = {90, 80, 40, 30, 20, 12, 10}; // 集装箱重量
        int[] weightLimits = {152, 130}; // 每艘船的载重限制

        ContainerShipping shipping = new ContainerShipping(containers, weightLimits);
        List<List<List<Integer>>> combinations = shipping.findCombinations();

        System.out.println("Possible combinations of containers:");
        for (List<List<Integer>> combination : combinations) {
            System.out.print("Ship 1: ");
            for (int weight : combination.get(0)) {
                System.out.print(weight + " ");
            }
            System.out.print("| Ship 2: ");
            for (int weight : combination.get(1)) {
                System.out.print(weight + " ");
            }
            System.out.println();
        }

        if (combinations.isEmpty()) {
            System.out.println("No valid combinations found.");
        }
    }
}

