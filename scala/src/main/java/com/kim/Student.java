package com.kim;

/**
 * Hello world!
 */
public class Student {

    private String name;

    private Integer age;

    private static String school = "mit";

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo() {
        System.out.println(this.name + " " + this.age + " " + Student.school);
    }

    public static void main(String[] args) {
        new Student("scq", 34).printInfo();
    }
}
