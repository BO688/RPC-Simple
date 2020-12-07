package com.PRC;

import com.PRC.config.MethodCusParams;
import com.PRC.config.MethodProParams;
import com.PRC.config.MethodReturn;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;
import java.util.Map;

public class RPCServer {
    HashMap<String, MethodProParams> map1=new HashMap<>();
    HashMap<ChannelId ,ChannelHandlerContext> map2=new HashMap<>();
    RPCServer(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
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
                                     MethodCusParams mcp=((MethodCusParams)msg);
                                     mcp.setFrom(ctx.channel().id());
                                     System.out.println(mcp);
                                     map2.put(ctx.channel().id(),ctx);
                                     SendParams(mcp);
                                 }else if(msg instanceof MethodProParams){
                                     System.out.println(msg);
                                     MethodProParams mpp=((MethodProParams)msg);
                                     mpp.setCtx(ctx);
                                     map1.put(mpp.getMethodName()+mpp.getClassName(),mpp);
                                 }else if(msg instanceof MethodReturn){
                                     System.out.println(msg);
                                    SendReturn((MethodReturn) msg);
                                 }
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
            ChannelFuture f = b.bind(688).sync();
            f.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    private void SendParams(MethodCusParams mcp) {
        for (Map.Entry<String,MethodProParams> e:map1.entrySet()) {
            if(e.getKey().equals(mcp.getMethodName()+mcp.getClassName())){
                e.getValue().getCtx().writeAndFlush(mcp);
                System.out.println("Send");
                break;
            }
        }
    }
    private void SendReturn(MethodReturn mr){
        for (Map.Entry<ChannelId,ChannelHandlerContext> e:map2.entrySet()) {
            if(e.getKey().equals(mr.getFrom())){
                e.getValue().writeAndFlush(mr);
                break;
            }
        }
    }
    public static void main(String[] args) {
        new RPCServer();
    }
}
