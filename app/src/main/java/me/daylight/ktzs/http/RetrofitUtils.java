package me.daylight.ktzs.http;

import me.daylight.ktzs.http.interceptor.AddCookiesInterceptor;
import me.daylight.ktzs.http.interceptor.HeadInterceptor;
import me.daylight.ktzs.http.interceptor.ReceivedCookiesInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private RetrofitUtils() {
    }

    public static Retrofit newInstance(String url) {
        Retrofit mRetrofit;
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        OkHttpClient client=builder.addInterceptor(new HeadInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }

}