package com.company.desginpatterns.dutychains;

public class CacheInterceptor implements Interceptor {

    @Override
    public String interceptor(Chain chain) {
        System.out.println("CacheInterceptor 拦截器 终点！");

        return "success";
    }
}
