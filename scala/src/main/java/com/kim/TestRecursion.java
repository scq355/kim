package com.kim;

public class TestRecursion {
    public static void main(String[] args) {
        System.out.println(factorial(5));
        System.out.println(fac(5));
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static int fac(int n) {
        if (n == 0) {
            return 1;
        }
        return fac(n - 1) * n;
    }
}
