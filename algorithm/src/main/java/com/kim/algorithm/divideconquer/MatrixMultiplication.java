package com.kim.algorithm.divideconquer;

public class MatrixMultiplication {

    // 矩阵乘法
    public static int[][] multiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        if (n == 1) {
            C[0][0] = A[0][0] * B[0][0]; // 基础情况
        } else {
            int half = n / 2;

            // 分割矩阵
            int[][] A11 = new int[half][half];
            int[][] A12 = new int[half][half];
            int[][] A21 = new int[half][half];
            int[][] A22 = new int[half][half];

            int[][] B11 = new int[half][half];
            int[][] B12 = new int[half][half];
            int[][] B21 = new int[half][half];
            int[][] B22 = new int[half][half];

            // 填充子矩阵
            for (int i = 0; i < half; i++) {
                for (int j = 0; j < half; j++) {
                    A11[i][j] = A[i][j];
                    A12[i][j] = A[i][j + half];
                    A21[i][j] = A[i + half][j];
                    A22[i][j] = A[i + half][j + half];

                    B11[i][j] = B[i][j];
                    B12[i][j] = B[i][j + half];
                    B21[i][j] = B[i + half][j];
                    B22[i][j] = B[i + half][j + half];
                }
            }

            // 计算 Strassen 的七个矩阵乘法
            int[][] M1 = multiply(add(A11, A22), add(B11, B22));
            int[][] M2 = multiply(add(A21, A22), B11);
            int[][] M3 = multiply(A11, subtract(B12, B22));
            int[][] M4 = multiply(A22, subtract(B21, B11));
            int[][] M5 = multiply(add(A11, A12), B22);
            int[][] M6 = multiply(subtract(A21, A11), add(B11, B12));
            int[][] M7 = multiply(subtract(A12, A22), add(B21, B22));

            // 组合结果矩阵
            int[][] C11 = add(subtract(add(M1, M4), M5), M7);
            int[][] C12 = add(M3, M5);
            int[][] C21 = add(M2, M4);
            int[][] C22 = add(subtract(add(M1, M3), M2), M6);

            // 填充结果矩阵 C
            for (int i = 0; i < half; i++) {
                for (int j = 0; j < half; j++) {
                    C[i][j] = C11[i][j];
                    C[i][j + half] = C12[i][j];
                    C[i + half][j] = C21[i][j];
                    C[i + half][j + half] = C22[i][j];
                }
            }
        }
        return C;
    }

    // 矩阵加法
    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    // 矩阵减法
    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    // 主方法
    public static void main(String[] args) {
        int[][] A = {
                {1, 2},
                {3, 4}
        };

        int[][] B = {
                {5, 6},
                {7, 8}
        };

        int[][] C = multiply(A, B);

        // 打印结果矩阵 C
        System.out.println("Result matrix C:");
        for (int[] row : C) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

