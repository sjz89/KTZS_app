package me.daylight.ktzs.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadInterceptor implements Interceptor {
    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                .addHeader("agent","Android")
                .build();
        return chain.proceed(request);
    }
}
