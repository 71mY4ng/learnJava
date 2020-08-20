package com.company.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetDemo {
    public static void main(String[] args) {
        try {
            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
            for (InetAddress addr: addresses
                 ) {
                System.out.println(addr);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
