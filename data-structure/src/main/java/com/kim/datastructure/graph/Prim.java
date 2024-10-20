package com.kim.datastructure.graph;

import java.util.Arrays;

class Prim {
    private static final int V = 5; // 顶点数量

    // 查找最小生成树
    public void primMST(int graph[][]) {
        int[] parent = new int[V]; // 存储生成树的父节点
        int[] key = new int[V]; // 存储最小权值
        boolean[] inMST = new boolean[V]; // 标记已包含在 MST 的顶点

        // 初始化
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(inMST, false);
        key[0] = 0; // 从第一个顶点开始
        parent[0] = -1; // 第一个节点是根节点

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, inMST);
            inMST[u] = true; // 将选定的顶点加入 MST

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        printMST(parent, graph);
    }

    // 找到键值中最小的顶点
    private int minKey(int key[], boolean inMST[]) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // 打印最小生成树
    private void printMST(int parent[], int graph[][]) {
        System.out.println("边 \t 权值");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + " \t " + graph[i][parent[i]]);
        }
    }

    public static void main(String[] args) {
        Prim prim = new Prim();
        int graph[][] = new int[][] {
                { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 }
        };
        prim.primMST(graph);
    }
}

