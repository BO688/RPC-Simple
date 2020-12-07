package com.PRC.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcClientProxy implements InvocationHandler {
    @SuppressWarnings("unchecked")
    public  static <T> T getProxy(Class<T> clazz,RPClientPro rpClientPro) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader()
                , new Class<?>[]{clazz},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        MethodCusParams mcp=new MethodCusParams();
                        System.out.println("开启事务");
                        mcp.setParamsType(method.getParameterTypes());
                        mcp.setMethodName(method.getName());
                        mcp.setParams(args);
                        mcp.setClassName(method.getDeclaringClass().getName());
                        mcp.setRequestId(UUID.randomUUID().toString());
//                        rpClientPro.map.put(mcp.getRequestId(),null);
                        rpClientPro.c.writeAndFlush(mcp);
//                        Object obj=method.invoke(new TestImp(), args);//代理类，参数
                        while (rpClientPro.map.get(mcp.getRequestId())==null){
                        Thread.sleep(100);
                        }
                        Object obj=rpClientPro.map.get(mcp.getRequestId());//代理类，参数
                        System.out.println("关闭事务");
                        return obj;
                    }
                });
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getDeclaringClass().getName());
        System.out.println(method.getName());
        System.out.println(args);
        System.out.println(method.getParameterTypes());
//        System.out.println(args.length);
        return null;
    }

    /**
     * This method is actually called when you use a proxy object to call a method.
     * The proxy object is the object you get through the getProxy method.
     */

}

