package com.PRC.config;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.io.Serializable;

@Data
public class MethodProParams implements Serializable {
    private static final long serialVersionUID = 4923011103118853877L;
    private Class<?>[] params;
    private String className;
    private String methodName;
    private ChannelHandlerContext ctx;
}
