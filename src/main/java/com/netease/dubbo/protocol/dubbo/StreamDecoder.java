/**
 * Copyright 2014-2019, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2019-08-13
 */
package com.netease.dubbo.protocol.dubbo;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.rpc.protocol.dubbo.DubboCodec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuangpeng
 * @since 2019-08-13
 */

public class StreamDecoder extends ByteToMessageDecoder {

    // header length.
    protected static final int HEADER_LENGTH = 16;

    // magic header.

    protected static final byte MAGIC_HIGH = (byte) 0xda;
    protected static final byte MAGIC_LOW = (byte) 0xbb;
    protected static final int SERIALIZATION_MASK = 0x1f;
    protected static final byte FLAG_REQUEST = (byte) 0x80;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        //解析dubbo协议
        //@See https://dubbo.apache.org/zh-cn/blog/dubbo-protocol.html
        try {
            System.out.println("######### 收到请求 ##########");
            System.out.println("一共" + byteBuf.readableBytes() + "字节");
            byte[] header = new byte[HEADER_LENGTH];
            /**
             * 第一步，读取dubbo协议头，共16字节
             */
            byteBuf.readBytes(header);
            /**
             * 第二步，检查输出Magic数
             */
            System.out.println("########## Magic Number ##########");
            System.out.println(Util.byteToHex(header[0]) + Util.byteToHex(header[1]));

            /**
             * 第三步，请求类型，序列化协议类型
             */
            byte flag = header[2], proto = (byte) (flag & SERIALIZATION_MASK);
            System.out.println("########## 请求类型 ##########");
            if ((flag & FLAG_REQUEST) == 0) {
                System.out.println("dubbo response");
            }else{
                System.out.println("dubbo request");
            }

            System.out.println("########## payload的序列化协议类型 ###########");
            System.out.println(Util.getSerialType(proto));

            // get status.
            byte status = header[3];
            System.out.println("########## status值 #######");
            System.out.println("status值:" + status);


            // get status.
            long id = Util.bytes2long(header,4);


            System.out.println("########### 请求id ##############" + id);
            System.out.println("请求id:" + id);

            // get data length.
            int len = Util.bytes2int(header, 12);

            System.out.println("############## 请求payload有效长度 #############");
            System.out.println("一共" + len + "字节");

            byte[] payLoad = new byte[len];
            /**
             * 第四步，读取有效载荷
             */
            byteBuf.readBytes(payLoad);
            Hessian2ObjectInput in = new Hessian2ObjectInput(new ByteArrayInputStream(payLoad));
            String dubboVersion = in.readUTF();
            System.out.println("########## Dubbo Version #########");
            System.out.println(dubboVersion);
            System.out.println("########## Service Name ##########");
            System.out.println(in.readUTF());
            System.out.println("########## Service version ##########");
            System.out.println(in.readUTF());
            System.out.println("########## Method Name ##########");
            System.out.println(in.readUTF());
            Object[] args;
            Class<?>[] pts;
            String desc = in.readUTF();
            System.out.println("########## Desc Name ##########");
            System.out.println(desc);
            if (desc.length() == 0) {
                pts = DubboCodec.EMPTY_CLASS_ARRAY;
                args = DubboCodec.EMPTY_OBJECT_ARRAY;
            } else {
                pts = ReflectUtils.desc2classArray(desc);
                args = new Object[pts.length];
                for (int i = 0; i < args.length; i++) {
                    try {
                        args[i] = in.readObject(pts[i]);
                    } catch (Exception e) {

                    }
                }
                System.out.println("######### Method parameter types ##########");
                System.out.println(JSON.toJSONString(pts));
                System.out.println("######### Method arguments value #########");
                System.out.println(JSON.toJSONString(args));
            }

            Map<String, String> map = (Map<String, String>) in.readObject(Map.class);
            if (map != null && map.size() > 0) {
                System.out.println("######## Attachments #########");
                System.out.println(JSON.toJSONString(map));
            }

        } finally {

        }
    }
}
