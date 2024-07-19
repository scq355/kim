package com.kim.utils;

import sun.misc.Unsafe;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;

public class JvmUtil {

    //  ManagementFactory是一个在运行时管理和监控Java VM的工厂类
    //  它能提供很多管理VM的静态接口的运行时实例，比如RuntimeMXBean
    //  RuntimeMXBean是Java虚拟机的运行时管理接口.
    //  取得VM运行管理实例，到管理接口句柄
    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String jvmInstanceName = runtimeMXBean.getName();
        return Integer.parseInt(jvmInstanceName.split("@")[0]);
    }

    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
