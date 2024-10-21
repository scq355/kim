package com.kim.algorithm.backtracking;

import java.util.ArrayList;
import java.util.List;

public class GraphColoring {

    private int[][] graph; // 图的邻接矩阵
    private int numVertices; // 图的顶点数
    private int[] colors; // 存储每个顶点的颜色
    private int maxColors; // 可用的最大颜色数
    private List<List<Integer>> results; // 存储所有有效着色方案

    public GraphColoring(int[][] graph, int maxColors) {
        this.graph = graph;
        this.numVertices = graph.length;
        this.maxColors = maxColors;
        this.colors = new int[numVertices];
        this.results = new ArrayList<>();
    }

    public List<List<Integer>> colorGraph() {
        backtrack(0);
        return results;
    }

    private void backtrack(int vertex) {
        // 如果所有顶点都已着色，记录当前方案
        if (vertex == numVertices) {
            List<Integer> currentColoring = new ArrayList<>();
            for (int color : colors) {
                currentColoring.add(color);
            }
            results.add(currentColoring);
            return;
        }

        // 尝试为当前顶点着色
        for (int color = 1; color <= maxColors; color++) {
            if (isSafe(vertex, color)) {
                colors[vertex] = color; // 着色
                backtrack(vertex + 1); // 递归处理下一个顶点
                colors[vertex] = 0; // 撤销选择
            }
        }
    }

    private boolean isSafe(int vertex, int color) {
        // 检查相邻顶点是否已着色为相同颜色
        for (int adj = 0; adj < numVertices; adj++) {
            if (graph[vertex][adj] == 1 && colors[adj] == color) {
                return false; // 不安全
            }
        }
        return true; // 安全
    }

    public static void main(String[] args) {
        // 图的邻接矩阵表示
        int[][] graph = {
                {0, 1, 1, 1},
                {1, 0, 0, 1},
                {1, 0, 0, 1},
                {1, 1, 1, 0}
        };
        int maxColors = 3; // 可用的最大颜色数

        GraphColoring gc = new GraphColoring(graph, maxColors);
        List<List<Integer>> colorings = gc.colorGraph();

        System.out.println("Possible colorings:");
        for (List<Integer> coloring : colorings) {
            System.out.println(coloring);
        }

        if (colorings.isEmpty()) {
            System.out.println("No valid colorings found.");
        }
    }
}

