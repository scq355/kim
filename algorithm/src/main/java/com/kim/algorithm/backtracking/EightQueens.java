package com.kim.algorithm.backtracking;

import java.util.ArrayList;
import java.util.List;

public class EightQueens {

    private List<List<String>> results; // 存储所有解
    private int[] queens; // 存储每一列皇后的位置

    public EightQueens() {
        results = new ArrayList<>();
        queens = new int[8]; // 8列
    }

    public List<List<String>> solveNQueens() {
        placeQueens(0);
        return results;
    }

    private void placeQueens(int row) {
        if (row == 8) { // 所有皇后都已放置
            results.add(generateBoard());
            return;
        }
        for (int col = 0; col < 8; col++) {
            if (isSafe(row, col)) {
                queens[row] = col; // 放置皇后
                placeQueens(row + 1); // 递归放置下一个皇后
            }
        }
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 检查列和两条对角线
            if (queens[i] == col ||
                    queens[i] - i == col - row ||
                    queens[i] + i == col + row) {
                return false;
            }
        }
        return true;
    }

    private List<String> generateBoard() {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                if (queens[i] == j) {
                    sb.append("Q"); // 皇后
                } else {
                    sb.append("."); // 空位
                }
            }
            board.add(sb.toString());
        }
        return board;
    }

    public static void main(String[] args) {
        EightQueens solution = new EightQueens();
        List<List<String>> results = solution.solveNQueens();
        System.out.println("Total solutions: " + results.size());
        for (List<String> board : results) {
            for (String row : board) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}

