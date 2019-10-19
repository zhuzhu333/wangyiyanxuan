package com.kgc.consumer.utils.wxPayUtils;

import java.util.UUID;

public class CommonUtil {

    public static String createUUID(int size){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,size);
    }
}
