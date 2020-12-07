package com.PRC.annotation;

import com.PRC.config.MethodCusParams;
import com.PRC.config.MethodProParams;
import io.netty.channel.Channel;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

public class AnnotationUtil {
    public static void SendTheMethodAnn(Channel channel, Class AnnotationClass) {
        Reflections reflections = new Reflections();
        Set<Class> classes = reflections.getTypesAnnotatedWith(AnnotationClass);
        Iterator <Class>i=classes.iterator();
        while (i.hasNext()){
            for (Method m :  i.next().getMethods()) {
                MethodProParams mpp = new MethodProParams();
                mpp.setClassName(classes.getClass().toString());
                mpp.setMethodName(m.getName());
                mpp.setParams(m.getParameterTypes());
                System.out.println(mpp);
                channel.writeAndFlush(mpp);
            }
        }

    }

    public static void SendTheMethod(Channel channel, Class AnnotationClass) {
        if (AnnotationClass.isInterface()) {
            SendTheMethodImp(channel, AnnotationClass);
        } else {
            SendTheMethodAnn(channel, AnnotationClass);
        }
    }

    public static void SendTheMethodImp(Channel channel, Class AnnotationClass) {
        for (Method m : AnnotationClass.getMethods()) {
            MethodProParams mpp = new MethodProParams();
            mpp.setClassName(AnnotationClass.getName());
            mpp.setMethodName(m.getName());
            mpp.setParams(m.getParameterTypes());
            System.out.println(mpp);
            channel.writeAndFlush(mpp);
        }
    }

    public static Object GetMethodImp(MethodCusParams mcp) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class Interface=Class.forName(mcp.getClassName());
        Reflections reflections = new Reflections
                (new ConfigurationBuilder().setUrls(ClasspathHelper.
                        forPackage(""))
                        .setScanners(new SubTypesScanner()));
        Set<Class  > classes =reflections.getSubTypesOf(Interface);
        Iterator<Class> i=classes.iterator();
        while (i.hasNext()){
            Class c=i.next();
          return c.getMethod(mcp.getMethodName(),mcp.getParamsType()).invoke(c.getConstructor().newInstance(),mcp.getParams());
        }
        return null;


    }

}


