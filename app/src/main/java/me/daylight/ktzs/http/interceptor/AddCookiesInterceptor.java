package me.daylight.ktzs.http.interceptor;

import java.io.IOException;
import java.util.Set;

import me.daylight.ktzs.app.KTZSApp;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    @SuppressWarnings("NullableProblems")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<String> preferences = SharedPreferencesUtil.getSet(KTZSApp.getApplication(),"config","cookie");
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
