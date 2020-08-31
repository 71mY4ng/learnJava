package com.company.desginpatterns.dutychains;

import java.util.List;

public class RealInterceptorChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;

    private int index;

    private String request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, String request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public String request() {
        return request;
    }

    @Override
    public String proceed(String request) {
        if (index >= interceptors.size()) {
            return null;
        }

        RealInterceptorChain next = new RealInterceptorChain(interceptors, index + 1, request);

        Interceptor interceptor = interceptors.get(index);

        return interceptor.interceptor(next);
    }
}
