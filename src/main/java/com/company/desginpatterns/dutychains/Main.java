package com.company.desginpatterns.dutychains;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Interceptor> interceptors = new ArrayList<>();

        interceptors.add(new BridgeInterceptor());
        interceptors.add(new RetryAndFollowInterceptor());
        interceptors.add(new CacheInterceptor());

        final RealInterceptorChain request = new RealInterceptorChain(interceptors, 0, "request");

        request.proceed("request");
    }
}
