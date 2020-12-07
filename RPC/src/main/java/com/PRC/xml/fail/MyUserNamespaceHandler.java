package com.PRC.xml.fail;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
public class MyUserNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("GetMethodPath", new UserBeanDefinitionParser());
    }

}
