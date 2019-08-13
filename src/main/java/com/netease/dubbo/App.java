package com.netease.dubbo;

import com.netease.dubbo.protocol.dubbo.NettyServer;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        NettyServer nettyServer = new NettyServer();
        try {
            nettyServer.doOpen();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
