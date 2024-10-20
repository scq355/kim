package com.kim.datastructure.graph;
import java.util.Arrays;
import java.util.Comparator;


public class Kruskal {
    private static class Edge {
        int source, destination, weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // 查找操作
    private int find(int parent[], int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }

    // 并合操作
    private void union(int parent[], int x, int y) {
        parent[x] = y;
    }

    public void kruskalMST(int graph[][]) {
        int V = graph.length; // 顶点数量
        Edge[] edges = new Edge[V * (V - 1) / 2]; // 边的数组
        int index = 0;

        // 创建边列表
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 0) {
                    edges[index++] = new Edge(i, j, graph[i][j]);
                }
            }
        }

        // 只保留有效的边
        edges = Arrays.copyOf(edges, index);

        // 按权值排序边
        Arrays.sort(edges, Comparator.comparingInt(e -> e.weight));

        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        System.out.println("边 \t 权值");
        for (Edge edge : edges) {
            int x = find(parent, edge.source);
            int y = find(parent, edge.destination);

            if (x != y) {
                System.out.println(edge.source + " - " + edge.destination + " \t " + edge.weight);
                union(parent, x, y);
            }
        }
    }

    public static void main(String[] args) {
        Kruskal kruskal = new Kruskal();
        int graph[][] = new int[][] {
                { 0, 10, 0, 30, 0 },
                { 10, 0, 50, 0, 0 },
                { 0, 50, 0, 20, 10 },
                { 30, 0, 20, 0, 60 },
                { 0, 0, 10, 60, 0 }
        };
        kruskal.kruskalMST(graph);
    }
}
