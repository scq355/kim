package com.kim.algorithm.backtracking;

import java.util.Arrays;

public class TravelingSalesman {

    private int[][] graph; // 邻接矩阵表示城市之间的距离
    private int numCities; // 城市数量
    private int minCost; // 最小费用
    private int[] bestPath; // 最优路径

    public TravelingSalesman(int[][] graph) {
        this.graph = graph;
        this.numCities = graph.length;
        this.minCost = Integer.MAX_VALUE; // 初始设为最大值
        this.bestPath = new int[numCities + 1]; // 存储最优路径
    }

    public void findOptimalTour() {
        boolean[] visited = new boolean[numCities]; // 记录城市是否已访问
        int[] path = new int[numCities + 1]; // 存储当前路径
        path[0] = 0; // 从第一个城市出发
        visited[0] = true; // 标记第一个城市为已访问
        backtrack(0, 0, 1, path, visited); // 进行回溯
    }

    private void backtrack(int currentCity, int currentCost, int depth, int[] path, boolean[] visited) {
        // 如果所有城市都已访问，检查返回成本
        if (depth == numCities) {
            int totalCost = currentCost + graph[currentCity][0]; // 返回起始城市的成本
            if (totalCost < minCost) {
                minCost = totalCost; // 更新最小成本
                System.arraycopy(path, 0, bestPath, 0, path.length);
                bestPath[depth] = 0; // 返回起始城市
            }
            return;
        }

        // 遍历所有城市
        for (int nextCity = 0; nextCity < numCities; nextCity++) {
            if (!visited[nextCity] && graph[currentCity][nextCity] != 0) { // 如果城市未访问且有路径
                visited[nextCity] = true; // 标记为已访问
                path[depth] = nextCity; // 添加到路径
                backtrack(nextCity, currentCost + graph[currentCity][nextCity], depth + 1, path, visited);
                visited[nextCity] = false; // 撤销选择
            }
        }
    }

    public void printOptimalTour() {
        System.out.println("Minimum cost: " + minCost);
        System.out.print("Best path: ");
        for (int city : bestPath) {
            System.out.print(city + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        TravelingSalesman tsp = new TravelingSalesman(graph);
        tsp.findOptimalTour();
        tsp.printOptimalTour();
    }
}

