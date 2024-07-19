package com.kim.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ReflectionUtil {
    public static String getCallMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[3].getMethodName();
    }

    public static String getCallClassMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String[] className = stackTrace[3].getClassName().split("\\.");
        return className[className.length - 1] + ":" + stackTrace[3].getMethodName();
    }

    public static String getNakeCallClassMethod() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String[] className = stackTrace[3].getClassName().split("\\.");
        return className[className.length - 1] + "." + stackTrace[3].getMethodName();
    }

    public static Class<?>[] getInterfaces(Class<?> targetClass) {
        List<Class<?>> interfaceList = Arrays.asList(targetClass.getInterfaces());
        HashSet<Class<?>> interfaceSet = new HashSet<>(interfaceList);

        Class<?> superclass = targetClass.getSuperclass();
        while (null != superclass) {
            interfaceList = Arrays.asList(superclass.getInterfaces());
            interfaceSet.addAll(interfaceList);
            superclass = superclass.getSuperclass();
        }

        return interfaceSet.toArray(new Class<?>[0]);
    }

    public static Object newProxyInstance(Object targetObject, InvocationHandler handler) {
        Class<?> targetObjectClass = targetObject.getClass();
        ClassLoader loader = targetObjectClass.getClassLoader();
        Class<?>[] targetInterfaces = ReflectionUtil.getInterfaces(targetObjectClass);
        return Proxy.newProxyInstance(loader, targetInterfaces, handler);
    }
}
