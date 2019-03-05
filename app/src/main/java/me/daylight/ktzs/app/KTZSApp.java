package me.daylight.ktzs.app;

import android.app.Application;

public class KTZSApp extends Application {
    private static KTZSApp application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }

    public static KTZSApp getApplication(){
        return application;
    }

}
