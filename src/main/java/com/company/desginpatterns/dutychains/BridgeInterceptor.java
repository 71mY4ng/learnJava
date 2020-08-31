package com.company.desginpatterns.dutychains;

public class BridgeInterceptor implements Interceptor {

    @Override
    public String interceptor(Chain chain) {
        System.out.println("before exec BridgeInterceptor 拦截器");

        final String proceed = chain.proceed(chain.request());

        System.out.println("after exec BridgeInterceptor 拦截器, 得: " + proceed);

        return proceed;
    }
}
