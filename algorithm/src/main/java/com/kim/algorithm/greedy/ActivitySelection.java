package com.kim.algorithm.greedy;

import java.util.Arrays;
import java.util.Comparator;

class Activity {
    int start;
    int end;

    Activity(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class ActivitySelection {

    // 方法实现活动安排
    public static void selectActivities(Activity[] activities) {
        // 按结束时间排序
        Arrays.sort(activities, Comparator.comparingInt(a -> a.end));

        System.out.println("Selected activities:");
        int lastEndTime = -1; // 初始化最后结束时间

        for (Activity activity : activities) {
            // 如果当前活动的开始时间大于或等于最后结束时间，选择该活动
            if (activity.start >= lastEndTime) {
                System.out.println("Activity(" + activity.start + ", " + activity.end + ")");
                lastEndTime = activity.end; // 更新最后结束时间
            }
        }
    }

    // 主程序
    public static void main(String[] args) {
        Activity[] activities = {
                new Activity(1, 3),
                new Activity(2, 5),
                new Activity(4, 6),
                new Activity(6, 7),
                new Activity(5, 9),
                new Activity(8, 9)
        };

        selectActivities(activities);
    }
}

