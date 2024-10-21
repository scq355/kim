package com.kim.algorithm.greedy;

import java.util.*;

import java.util.*;

public class PrimAlgorithm {

    static class Edge {
        int vertex;
        int weight;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    public static void prim(int[][] graph) {
        int numVertices = graph.length;
        boolean[] inMST = new boolean[numVertices];
        int[] minEdge = new int[numVertices]; // 记录到当前树的最小边权重
        int[] parent = new int[numVertices];  // 记录每个节点的父节点

        Arrays.fill(minEdge, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        minEdge[0] = 0; // 从第一个顶点开始

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(0, 0)); // 初始顶点

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.vertex;

            if (inMST[u]) continue; // 如果已在 MST 中，跳过
            inMST[u] = true;

            // 遍历所有邻接边
            for (int v = 0; v < numVertices; v++) {
                if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < minEdge[v]) {
                    minEdge[v] = graph[u][v]; // 更新最小边权重
                    parent[v] = u;             // 更新父节点
                    pq.add(new Edge(v, graph[u][v]));
                }
            }
        }

        // 打印结果
        System.out.println("Edges in Minimum Spanning Tree (Prim's Algorithm):");
        for (int i = 1; i < numVertices; i++) {
            System.out.println("Edge: " + parent[i] + " - " + i + " - Weight: " + minEdge[i]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 2, 0, 6, 0},
                {2, 0, 3, 8, 5},
                {0, 3, 0, 0, 7},
                {6, 8, 0, 0, 9},
                {0, 5, 7, 9, 0}
        };

        prim(graph);
    }
}


