package com.company.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHostDemo {
    public static void main(String[] args) {
        try {
            InetAddress me = InetAddress.getLocalHost();
            String localhost = me.getCanonicalHostName();   // 计算机名 通过DNS来解析计算机名，并且缓存
            String dottedQuad = me.getHostAddress();
            String ipLength = "";
            if (me.getAddress().length == 4)
                ipLength = "IPv4";
            if (me.getAddress().length == 16)
                ipLength = "IPv6";



            System.out.println(localhost);
            System.out.println(dottedQuad);
            System.out.println("my ip version is " + ipLength);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
