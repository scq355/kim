package com.kim.algorithm.greedy;

import java.util.*;

public class DijkstraAlgorithm {

    static class Edge {
        int vertex;
        int weight;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    public static void dijkstra(int[][] graph, int source) {
        int numVertices = graph.length;
        int[] distances = new int[numVertices]; // 存储最短距离
        boolean[] visited = new boolean[numVertices]; // 标记已访问的节点

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0; // 源节点到自己的距离为0

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.add(new Edge(source, 0)); // 初始顶点

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.vertex;

            if (visited[u]) continue; // 如果已访问，跳过
            visited[u] = true;

            // 遍历所有邻接边
            for (int v = 0; v < numVertices; v++) {
                if (graph[u][v] != 0 && !visited[v]) { // 存在边且未访问
                    int newDist = distances[u] + graph[u][v];
                    if (newDist < distances[v]) { // 更新最短距离
                        distances[v] = newDist;
                        pq.add(new Edge(v, newDist)); // 加入优先队列
                    }
                }
            }
        }

        // 打印结果
        System.out.println("Shortest distances from source vertex " + source + ":");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("Vertex " + i + " - Distance: " + distances[i]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 7, 0, 0, 0, 2},
                {7, 0, 6, 0, 0, 1},
                {0, 6, 0, 5, 0, 0},
                {0, 0, 5, 0, 8, 0},
                {0, 0, 0, 8, 0, 3},
                {2, 1, 0, 0, 3, 0}
        };

        int source = 0; // 源顶点
        dijkstra(graph, source);
    }
}

