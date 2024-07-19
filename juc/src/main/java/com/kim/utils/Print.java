package com.kim.utils;

import java.util.Scanner;

public class Print {

    public static void tco(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "]" + ":" + s;
        // 提交线程池异步输出
        ThreadUtil.seqExecute(() -> System.out.println(cft));
    }

    public static void synTco(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "]" + ":" + s;
        System.out.println(cft);
    }

    public static void o(Object s) {
        Print.tco(s);
    }

    public static void fo(Object s) {
        String cft = "[" + ReflectionUtil.getNakeCallClassMethod() + "]";
        ThreadUtil.seqExecute(() -> System.out.println(cft + ": " + s));
    }

    public static synchronized void cfo(Object s) {
        String cft = "[" + ReflectionUtil.getNakeCallClassMethod() + "]";
        ThreadUtil.seqExecute(() -> System.out.println(cft + ": " + s));
    }

    public static void tcfo(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "|" + ReflectionUtil.getNakeCallClassMethod() + "]";
        System.out.println(cft + ": " + s);
    }

    public static void hint(Object s) {
        Print.tcfo("/--" + s + "--/");
    }

    public static String consoleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sth: ");
        return scanner.nextLine();
    }
}
