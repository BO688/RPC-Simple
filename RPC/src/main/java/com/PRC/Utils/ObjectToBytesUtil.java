package com.PRC.Utils;

import java.io.*;

public class ObjectToBytesUtil {
    public static byte[] ObjectToByteArray(Object obj) throws IOException {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        byte [] bytes=baos.toByteArray();
        baos.close();
        return bytes;
    }
    public static Object ByteArrayToObject(byte[] b) throws IOException, ClassNotFoundException {
        ByteArrayInputStream baos= new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(baos);
        Object o=ois.readObject();
        ois.close();
        baos.close();
        return o;
    }
}
