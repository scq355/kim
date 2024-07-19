package com.kim.entity;

import java.io.Serializable;

public class User implements Serializable {
    String uid;

    String nickName;

    public volatile int age;

    public User(String uid, String nickName) {
        this.uid = uid;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                '}';
    }

    public int getAge() {
        return age;
    }

    public String getNickName() {
        return nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
