package com.company.desginpatterns.dutychains;

public class RetryAndFollowInterceptor implements Interceptor {

    @Override
    public String interceptor(Chain chain) {
        System.out.println("before exec RetryAndFollowInterceptor 拦截器");

        final String proceed = chain.proceed(chain.request());

        System.out.println("after exec RetryAndFollowInterceptor 拦截器, 得: " + proceed);

        return proceed;
    }
}
