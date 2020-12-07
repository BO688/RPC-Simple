package com.PRC.config;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.Data;

import java.io.Serializable;

@Data
public class MethodCusParams implements Serializable {
    private static final long serialVersionUID = 4923011803118853877L;
    private String RequestId;
    private Object[] params;
    private Class<?>[] paramsType;
    private String className;
    private String methodName;
    private ChannelId from;
    private ChannelHandlerContext ctx;
}
