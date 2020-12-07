package com.PRC.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class TestAnnotation {
//    //获取RequestMappingh注解的value值以及声明类、方法名
//    Reflections reflections = new Reflections(new ConfigurationBuilder()
//            .setUrls(ClasspathHelper.forPackage("com.example.demo"))
//            .setScanners(new MethodAnnotationsScanner())
//    );
//Set<Method> methodSet = reflections.getMethodsAnnotatedWith(RequestMapping.class);
//for (Method method : methodSet){
//        //可以获取到注解中的参数
//        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
//        mapping.value();//获取注解中value的属性值
//        //获取声明了该方法的实例的字节码对象，通过反射就可以肆意妄为了
//        Class<?> declaringClass = method.getDeclaringClass();
//        method.getName();获取方法名
//    }
    public  void test(int a){
        System.out.println("test");
    }
    public  static void test(){
        System.out.println("test");
    }
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
//        Class.forName("com.PRC.annotation.TestAnnotation").getMethod("test").invoke(null);
//        String str = "shfsfs";
//        //包开头是com表示是sun内部用的，java打头的才是用户的
//        Method mtCharAt = String.class.getMethod("charAt", int.class);
//        Object ch = mtCharAt.invoke(str,1);//若第一个参数是null，则肯定是静态方法
//        System.out.println(ch);
//
//        System.out.println(mtCharAt.invoke(str, new Object[]{2}));//1.4语法
//        System.out.println( JSONObject.parseObject(JSON.toJSONString("sda")));
//        System.out.println(new String(RPCServer.ObjectToByteArray(new MethodProParams())));
        AnnotationUtil.SendTheMethodAnn(null,ProvideMethod.class);
    }
}
