package com.company.desginpatterns.dutychains;

/**
 * https://juejin.im/post/6844903743951994887
 * @desc 模仿 okhttp interceptor
 */
public interface Interceptor {

    String interceptor(Chain chain);

    interface Chain {
        String request();

        String proceed(String request);
    }
}
