package com.kim.datastructure.graph;

public class DirectedGraph {
    private int[][] adjacencyMatrix; // 邻接矩阵
    private int numberOfVertices; // 顶点数量

    // 构造函数
    public DirectedGraph(int size) {
        numberOfVertices = size;
        adjacencyMatrix = new int[size][size];

        // 初始化邻接矩阵
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = 0; // 0表示没有边
            }
        }
    }

    // 添加边
    public void addEdge(int source, int destination) {
        if (source >= numberOfVertices || destination >= numberOfVertices || source < 0 || destination < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        adjacencyMatrix[source][destination] = 1; // 设置为1表示有向边
    }

    // 删除边
    public void removeEdge(int source, int destination) {
        if (source >= numberOfVertices || destination >= numberOfVertices || source < 0 || destination < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        adjacencyMatrix[source][destination] = 0; // 设置为0表示没有边
    }

    // 打印邻接矩阵
    public void printGraph() {
        System.out.println("邻接矩阵:");
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 查找邻接节点
    public void findAdjacent(int vertex) {
        if (vertex >= numberOfVertices || vertex < 0) {
            System.out.println("无效的顶点索引");
            return;
        }
        System.out.print("顶点 " + vertex + " 的邻接节点: ");
        for (int i = 0; i < numberOfVertices; i++) {
            if (adjacencyMatrix[vertex][i] == 1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 创建一个有向图，包含5个顶点
        DirectedGraph graph = new DirectedGraph(5);

        // 添加边
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(4, 3);

        // 打印图
        graph.printGraph();

        // 查找邻接节点
        graph.findAdjacent(1);

        // 删除边
        graph.removeEdge(1, 3);

        // 打印图
        graph.printGraph();
    }
}

