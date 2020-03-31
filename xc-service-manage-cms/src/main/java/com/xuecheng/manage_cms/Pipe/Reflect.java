package com.xuecheng.manage_cms.Pipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

public class Reflect {

    public static void main(String[] args) throws Exception {
        //可以创建任意类的对象，可以执行任意类的方法
        //1.创建properties对象
        Properties pro = new Properties();
        //2.获取class目录下的配置文件,加载配置文件
        ClassLoader classLoader = Reflect.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("pro.properties");
        pro.load(is);

        //3.获取配置文件中的定义变量
        String className = pro.getProperty("className");
        String methodName = pro.getProperty("methodName");

        //4.加载该类进内存
        Class cls = Class.forName(className);
        //5.创建构造函数/对象
        Constructor constructor = cls.getConstructor(String.class,int.class);
        Object obj = constructor.newInstance("zhang",30);

        //6.获取方法对象
        Method method = cls.getMethod(methodName);
        //7.执行方法
        method.invoke(obj);
    }
}
