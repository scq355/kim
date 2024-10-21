package com.kim.algorithm.greedy;

import java.util.*;

public class KruskalAlgorithm {

    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    static class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]); // 路径压缩
            }
            return parent[u];
        }

        void union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);

            if (rootU != rootV) {
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
        }
    }

    public static void kruskal(List<Edge> edges, int numVertices) {
        Collections.sort(edges); // 按权重排序
        DisjointSet ds = new DisjointSet(numVertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;

            if (ds.find(u) != ds.find(v)) { // 检查是否形成环
                ds.union(u, v);
                mst.add(edge);
            }
        }

        // 打印结果
        System.out.println("Edges in Minimum Spanning Tree (Kruskal's Algorithm):");
        for (Edge edge : mst) {
            System.out.println("Edge: " + edge.src + " - " + edge.dest + " - Weight: " + edge.weight);
        }
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 2));
        edges.add(new Edge(0, 3, 6));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 3, 8));
        edges.add(new Edge(1, 4, 5));
        edges.add(new Edge(2, 4, 7));
        edges.add(new Edge(3, 4, 9));

        int numVertices = 5; // 顶点数量
        kruskal(edges, numVertices);
    }
}

