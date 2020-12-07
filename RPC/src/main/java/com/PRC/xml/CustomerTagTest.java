package com.PRC.xml;

public class CustomerTagTest {
    public static void main(String[] args) throws Exception {
        Beans beans=new Beans("test.xml");

        System.out.println(beans.getPath("cat"));
        System.out.println(beans.getPath("dog"));
//        ApplicationContext beans=new ClassPathXmlApplicationContext("classpath:RPC.xml");
//        System.out.println(beans.getBean("GetMethodPath"));

    }
//    @Test
//    public void Test1(){
//        try {
//
//            Beans beans = new Beans("test.xml");
//
//            Cat cat = (Cat) beans.getBean("cat");
//            cat.say();
//
//            System.out.println("----------------------------------");
//
//            Dog dog = (Dog) beans.getBean("dog");
//            dog.say();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
