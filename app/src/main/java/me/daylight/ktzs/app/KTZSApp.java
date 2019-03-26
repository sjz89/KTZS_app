package me.daylight.ktzs.app;

import android.app.Application;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public class KTZSApp extends Application {
    private static KTZSApp application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        QMUISwipeBackActivityManager.init(this);
    }

    public static KTZSApp getApplication(){
        return application;
    }

}
