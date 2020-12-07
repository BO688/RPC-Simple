package com.PRC.config;

import com.PRC.annotation.AnnotationUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;

import static com.PRC.annotation.AnnotationUtil.GetMethodImp;

public class RPClientPro extends Thread{
    public  Channel c;
     HashMap<String ,Object> map=new HashMap<>();
     Class<?> [] classes;
     public   boolean Check=true;
    public RPClientPro(Class<?>[] classes){
        this.classes=classes;
    }
    public RPClientPro(){}

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new ObjectDecoder(1024 *1024, ClassResolvers
                                    .cacheDisabled(getClass().getClassLoader())));
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            if (msg instanceof MethodCusParams){
                                System.out.println(msg);
                                MethodCusParams mcp=(MethodCusParams)msg;
                                MethodReturn mr=new MethodReturn();
                                mr.setRequestId(mcp.getRequestId());
                                mr.setFrom(mcp.getFrom());
                                try {
                                    mr.setReturn(GetMethodImp(mcp));
                                    System.out.println(mr);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }finally {
                                    ctx.writeAndFlush(mr);
                                }
                                }else if(msg instanceof MethodReturn){
                                System.out.println(((MethodReturn) msg).getReturn());
                                map.put(((MethodReturn) msg).getRequestId(),((MethodReturn) msg).getReturn());
                            }
                        }
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                            AnnotationUtil.SendTheMethodAnn(ctx.channel(), ProvideMethod.class);
                            if(classes!=null){
                                for (Class<?> c:classes) {
                                    AnnotationUtil.SendTheMethodImp(ctx.channel(),c);
                                }
                            }
//                            else{
//                                AnnotationUtil.SendTheMethodImp(ctx.channel(), Test.class);
//                            }
                            Check=false;
                        }
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            ctx.close();
                            cause.printStackTrace();
                        }
                    });
                }
            });
            // 启动客户端
            ChannelFuture f = b.connect("127.0.0.1", 688).sync();
            c=f.channel();
            c.closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
