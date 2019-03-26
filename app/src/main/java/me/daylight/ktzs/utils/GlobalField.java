package me.daylight.ktzs.utils;

import me.daylight.ktzs.app.KTZSApp;

public class GlobalField {
    public static String url="http://"+SharedPreferencesUtil.getString(KTZSApp.getApplication().getApplicationContext(),
            GlobalField.SETTING,GlobalField.URL)+"/";
    public static final String USER="User";
    public static final String SETTING="Setting";

    public static final String URL="url";

    public static final int REQUEST_CODE_CHOOSE=100;

    public static final int Event_Channel_SignInDetail=0x00;
    public static final int Event_Channel_SignInCount=0x01;
    public static final int Event_Channel_Notice=0x02;

}
