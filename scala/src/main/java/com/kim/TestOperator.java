package com.kim;

public class TestOperator {

    public static void main(String[] args) {
        String s1 = "a";
        String s2 = new String("a");
        System.out.println(s2 == s1);
        System.out.println(s2.equals(s1));

        byte b = 10;
        b = (byte) (b + 1);
        b += 1;
        System.out.println(b);

        int x = 15;
        int y = x++;
        System.out.println(x + " " + y);

        x = 15;
        y = ++x;
        System.out.println(x + " " + y);

        x = 15;
        x = x++;
        System.out.println(x);
    }
}
