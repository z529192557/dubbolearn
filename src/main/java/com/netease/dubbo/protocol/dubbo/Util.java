/**
 * Copyright 2014-2019, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2019-08-13
 */
package com.netease.dubbo.protocol.dubbo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhuangpeng
 * @since 2019-08-13
 */
public class Util {

    static  byte HESSIAN2_SERIALIZATION_ID = 2;
    static byte JAVA_SERIALIZATION_ID = 3;
    static byte COMPACTED_JAVA_SERIALIZATION_ID = 4;
    static byte FASTJSON_SERIALIZATION_ID = 6;
    static byte NATIVE_JAVA_SERIALIZATION_ID = 7;
    static byte KRYO_SERIALIZATION_ID = 8;
    static byte FST_SERIALIZATION_ID = 9;
    static byte NATIVE_HESSIAN_SERIALIZATION_ID = 10;
    static byte PROTOSTUFF_SERIALIZATION_ID = 12;
    static byte AVRO_SERIALIZATION_ID = 11;
    static  byte GSON_SERIALIZATION_ID = 16;
    static byte PROTOBUF_JSON_SERIALIZATION_ID = 21;

    static Map<Byte,String> type2String = new HashMap<>();
    static{
        type2String.put(HESSIAN2_SERIALIZATION_ID,"HESSIAN2");
        type2String.put(JAVA_SERIALIZATION_ID,"JAVA");
        type2String.put(COMPACTED_JAVA_SERIALIZATION_ID,"COMPACTED");
        type2String.put(FASTJSON_SERIALIZATION_ID,"FASTJSON");
        type2String.put(NATIVE_JAVA_SERIALIZATION_ID,"NATIVE_JAVA");
        type2String.put(KRYO_SERIALIZATION_ID,"KRYO");
        type2String.put(FST_SERIALIZATION_ID,"FST");
        type2String.put(NATIVE_HESSIAN_SERIALIZATION_ID,"NATIVE_HESSIAN");
        type2String.put(PROTOSTUFF_SERIALIZATION_ID,"PROTOSTUFF");
        type2String.put(AVRO_SERIALIZATION_ID,"AVRO");
        type2String.put(GSON_SERIALIZATION_ID,"GSON");
        type2String.put(PROTOBUF_JSON_SERIALIZATION_ID,"PROTOBUF_JSON");
    }



    public static final char[] HEX_CHAR = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    public static String byteToHex(byte value){
        char[] buf = new char[2];;
        buf[0] = HEX_CHAR[value >>> 4 & 0xf];
        buf[1] = HEX_CHAR[value & 0xf];
        return new String(buf);
    }

    public static String getSerialType(byte value){
        return type2String.get(value);
    }

    /**
     * to long.
     *
     * @param b   byte array.
     * @param off offset.
     * @return long.
     */
    public static long bytes2long(byte[] b, int off) {
        return ((b[off + 7] & 0xFFL) << 0) +
                ((b[off + 6] & 0xFFL) << 8) +
                ((b[off + 5] & 0xFFL) << 16) +
                ((b[off + 4] & 0xFFL) << 24) +
                ((b[off + 3] & 0xFFL) << 32) +
                ((b[off + 2] & 0xFFL) << 40) +
                ((b[off + 1] & 0xFFL) << 48) +
                (((long) b[off + 0]) << 56);
    }

    /**
     * to int.
     *
     * @param b   byte array.
     * @param off offset.
     * @return int.
     */
    public static int bytes2int(byte[] b, int off) {
        return ((b[off + 3] & 0xFF) << 0) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off + 0]) << 24);
    }
}
