package com.lbsky.sysenv.util;


import com.lbsky.sysenv.datatype.CommonConfigInfo;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectReflectUtil {

    static void setObjectVal(Object o, String fieldName, Object value) {
        Method method = getSetMethod(o.getClass(), fieldName);

        try {

            if (method == null) {   //如果set方法没找到就找add方法
                method = getAddMethod(o.getClass(), fieldName);
            }

            if (method == null) {
                o.getClass().getField(fieldName).set(o, value);
            } else {
                String paramType = (method.getParameterTypes()[0]).getName();
                switch (paramType) {
                    case "boolean":
                        method.invoke(o, Boolean.valueOf(value.toString()));
                        break;
                    case "int":
                        method.invoke(o, Integer.parseInt(value.toString()));
                        break;
                    default:
                        method.invoke(o, value);
                }
            }
        } catch (Exception e) {
            String paramType = null;
            String methodName = "";
            if (method != null) {
                paramType = (method.getParameterTypes()[0]).getName();
                methodName = method.getName();
            }
            System.out.println("object is:" + o.getClass().getName() + "\t method is:" + methodName +
                    "\t paramType is:" + paramType + "\t value is:" + value);
            e.printStackTrace();
        }
    }

    private static Method getSetMethod(Class<?> _class, String fieldName) {
        Map<String, Method> allSetMethods = getAllGetOrSetMethodMap(_class);
        String methodName = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
        return allSetMethods.get(methodName);
    }

    private static Method getGetMethod(Class<?> _class, String fieldName) {
        Map<String, Method> allGetMethods = getAllGetOrSetMethodMap(_class);
        String methodName = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
        return allGetMethods.get(methodName);
    }

    private static Method getAddMethod(Class<?> _class, String fieldName) {
        Map<String, Method> allGetMethods = getAllGetOrSetMethodMap(_class);
        String methodName = "add" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
        return allGetMethods.get(methodName);
    }

    private static Map<String, Method> getAllGetOrSetMethodMap(Class<?> _class) {
        Map<String, Method> methodMap = new ConcurrentHashMap<>();
        Method[] methods = _class.getDeclaredMethods();
        String methodName, methodNamePre;
        for (Method method : methods) {
            methodName = method.getName();
            methodNamePre = methodName.substring(0, 3);
            if ("get".equals(methodNamePre) || "set".equals(methodNamePre) || "add".equals(methodNamePre)) {
                method.setAccessible(true);
                methodMap.put(methodName, method);
            }
        }
        return methodMap;
    }

    static CommonConfigInfo getInfoInstanceByBeanName(String beanName) {
        try {
            String beanPakage = "com.lbsky.sysenv.datatype";
            Class classz = Class.forName(beanPakage + "." + beanName);
            return (CommonConfigInfo) classz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("sysenv getInfoInstanceByBeanName occured a exception, beanName is : " + beanName);
            throw new RuntimeException(e);
        }
    }
}
