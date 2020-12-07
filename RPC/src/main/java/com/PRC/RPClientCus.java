package com.PRC;

import com.PRC.Service.Test1;
import com.PRC.config.RPClientPro;
import com.PRC.config.RpcClientProxy;
import com.PRC.xml.Beans;

public class RPClientCus {
    public RPClientPro RPContextApplication() throws InterruptedException {
        RPClientPro rpClientPro=new RPClientPro();
        rpClientPro.start();
        while (rpClientPro.Check){
            Thread.sleep(100);
        }
        return rpClientPro;

    }
    public RPClientPro RPContextApplication(String Filename) throws Exception {
        Beans beans=new Beans(Filename);
        RPClientPro rpClientPro=new RPClientPro(beans.getBeansList().toArray(new Class[]{}));
        rpClientPro.start();
        while (rpClientPro.Check){
            Thread.sleep(100);
        }
        return rpClientPro;
    }
    public static void main(String[] args) throws InterruptedException {

        RPClientPro rpClientPro=new RPClientCus().RPContextApplication();
        Test1 mc= RpcClientProxy.getProxy(Test1.class,rpClientPro);
        System.out.println(mc.SayNumber());
    }
}
