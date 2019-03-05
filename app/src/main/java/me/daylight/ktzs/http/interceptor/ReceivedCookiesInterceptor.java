package me.daylight.ktzs.http.interceptor;

import java.io.IOException;
import java.util.HashSet;

import me.daylight.ktzs.app.KTZSApp;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));

            SharedPreferencesUtil.putValue(KTZSApp.getApplication(),"config","cookie", cookies);

        }

        return originalResponse;
    }
}
