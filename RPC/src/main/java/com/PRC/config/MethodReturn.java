package com.PRC.config;

import io.netty.channel.ChannelId;
import lombok.Data;

import java.io.Serializable;
@Data
public class MethodReturn implements Serializable {
    private static final long serialVersionUID = 4923081103118851117L;
    private Object Return;
    private ChannelId from;
    private String RequestId;
}
