package com.kim.datastructure.graph;

import java.util.Arrays;
import java.util.PriorityQueue;

class Dijkstra {
    private static class Node implements Comparable<Node> {
        int vertex;
        int weight;

        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public void dijkstra(int graph[][], int startVertex) {
        int V = graph.length; // 顶点数量
        int[] distances = new int[V]; // 存储最短路径
        boolean[] visited = new boolean[V]; // 标记已访问的顶点

        // 初始化距离数组
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Node(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            int currentVertex = currentNode.vertex;

            if (visited[currentVertex]) {
                continue;
            }
            visited[currentVertex] = true;

            // 更新邻接节点的距离
            for (int neighbor = 0; neighbor < V; neighbor++) {
                if (graph[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    int newDist = distances[currentVertex] + graph[currentVertex][neighbor];
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        priorityQueue.add(new Node(neighbor, newDist));
                    }
                }
            }
        }

        // 打印最短路径
        printSolution(distances);
    }

    // 打印最短路径
    private void printSolution(int distances[]) {
        System.out.println("顶点 \t 最短路径");
        for (int i = 0; i < distances.length; i++) {
            System.out.println(i + " \t " + (distances[i] == Integer.MAX_VALUE ? "INF" : distances[i]));
        }
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        int graph[][] = new int[][] {
                { 0, 1, 4, 0, 0, 0 },
                { 1, 0, 4, 2, 7, 0 },
                { 4, 4, 0, 3, 0, 0 },
                { 0, 2, 3, 0, 5, 0 },
                { 0, 7, 0, 5, 0, 6 },
                { 0, 0, 0, 0, 6, 0 }
        };
        dijkstra.dijkstra(graph, 0);
    }
}
