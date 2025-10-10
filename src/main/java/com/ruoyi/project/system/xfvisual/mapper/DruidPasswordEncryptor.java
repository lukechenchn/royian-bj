package com.ruoyi.project.system.xfvisual.mapper;

import com.alibaba.druid.filter.config.ConfigTools;

public class DruidPasswordEncryptor {
    public static void main(String[] args) throws Exception {
        String password = "";
        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
    }


}
