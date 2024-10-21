package com.kim.algorithm.divideconquer;

import java.util.Arrays;
import java.util.Comparator;

class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class ClosestPair {

    // 主方法
    public static double closestPair(Point[] points) {
        // 按 x 坐标排序
        Arrays.sort(points, Comparator.comparingDouble(p -> p.x));
        return closestPairRec(points, 0, points.length - 1);
    }

    // 递归方法
    private static double closestPairRec(Point[] points, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(points, left, right);
        }

        int mid = left + (right - left) / 2;
        double midX = points[mid].x;

        // 递归计算左半部分和右半部分的最小距离
        double d1 = closestPairRec(points, left, mid);
        double d2 = closestPairRec(points, mid + 1, right);

        // 取最小距离
        double d = Math.min(d1, d2);

        // 创建一个临时数组存储跨越中线的点
        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midX) < d) {
                strip[j] = points[i];
                j++;
            }
        }

        // 在 strip 中找到最小距离
        return Math.min(d, stripClosest(strip, j, d));
    }

    // 暴力法求最小距离
    private static double bruteForce(Point[] points, int left, int right) {
        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                minDist = Math.min(minDist, distance(points[i], points[j]));
            }
        }
        return minDist;
    }

    // 在 strip 中找到最小距离
    private static double stripClosest(Point[] strip, int size, double d) {
        // 按 y 坐标排序
        Arrays.sort(strip, 0, size, Comparator.comparingDouble(p -> p.y));

        double minDist = d;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < minDist; j++) {
                minDist = Math.min(minDist, distance(strip[i], strip[j]));
            }
        }

        return minDist;
    }

    // 计算两点之间的距离
    private static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // 主程序
    public static void main(String[] args) {
        Point[] points = {
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(2.0, 2.0),
                new Point(3.0, 3.0),
                new Point(0.5, 0.5)
        };

        double minDist = closestPair(points);
        System.out.println("The minimum distance is: " + minDist);
    }
}

