package com.PRC.xml;

import lombok.Data;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Data
public class Beans implements IBeans{

    private   Map<String,Object> beansObject = new HashMap<>();
    private  Map<String,String> beansPath = new HashMap<>();
    private  List<Class> beansList = new LinkedList<>();
    public Beans(String configxml) throws Exception {
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //xml文件的位置
        URL sources = Beans.class.getClassLoader().getResource(configxml);
        //创建document对象,并读取xml文件 （解析xml文件）
        Document document = reader.read(sources);
        //读取元素   getRootElement() --》 获取父节点  elements() --> 所有节点
        List<Element> elements = document.getRootElement().elements();
        for (Element element : elements) {
            String id = element.attributeValue("id");//获取id属性
            String path = element.attributeValue("path");//获取class属性
            String clazz = element.attributeValue("class");//获取class属性
            //  id （钥匙）  对应 一个 实例
            try {
                beansList.add(Class.forName(path));
                beansPath.put(id,path);
                beansObject.put(id,Class.forName(clazz).newInstance());
            }catch (Exception e){

            }

        }

    }
    public Object getBean(String name) {
        //根据名字来获取实例
        return beansObject.get(name);
    }

    @Override
    public String getPath(String name) {
        return beansPath.get(name);
    }
}
