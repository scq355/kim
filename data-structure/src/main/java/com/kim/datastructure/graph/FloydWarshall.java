package com.kim.datastructure.graph;

public class FloydWarshall {
    private static final int INF = Integer.MAX_VALUE; // 表示无穷大

    public void floydWarshall(int graph[][]) {
        int V = graph.length; // 顶点数量
        int[][] dist = new int[V][V];

        // 初始化距离矩阵
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    dist[i][j] = 0; // 从顶点到自身的距离为 0
                } else if (graph[i][j] != 0) {
                    dist[i][j] = graph[i][j]; // 直接边的距离
                } else {
                    dist[i][j] = INF; // 无边时设为无穷大
                }
            }
        }

        // 进行动态规划更新距离矩阵
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // 打印最终的最短路径矩阵
        printSolution(dist);
    }

    // 打印距离矩阵
    private void printSolution(int dist[][]) {
        int V = dist.length;
        System.out.println("最短路径矩阵:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        FloydWarshall floydWarshall = new FloydWarshall();
        int graph[][] = new int[][] {
                { 0, 3, 0, 0, 0, 0 },
                { 2, 0, 0, 1, 0, 0 },
                { 0, 7, 0, 0, 2, 0 },
                { 6, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 5, 0, 0 },
                { 0, 0, 0, 0, 3, 0 }
        };
        floydWarshall.floydWarshall(graph);
    }
}

