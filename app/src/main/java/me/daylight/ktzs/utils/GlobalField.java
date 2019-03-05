package me.daylight.ktzs.utils;

import me.daylight.ktzs.app.KTZSApp;

public class GlobalField {
    public static String url="http://"+SharedPreferencesUtil.getString(KTZSApp.getApplication().getApplicationContext(),
            GlobalField.SETTING,GlobalField.URL)+"/";
    public static final String USER="User";
    public static final String PASSWORD="Password";
    public static final String SETTING="Setting";

    public static final String ACCOUNT="Account";
    public static final String STATE="state";
    public static final String URL="url";

    public static final int MessageType_Send=1;
    public static final int MessageType_Receive=2;

    public static final int REQUEST_CODE_CHOOSE=100;

    public static final int InfoType_INIT=1;
    public static final int InfoType_ME =2;
    public static final int InfoType_FRIEND=3;
    public static final int InfoType_ADD=4;
    public static final int InfoType_ACCEPT=5;

    public static final int WebSocket_Type_Message=101;
    public static final int WebSocket_Type_HeartBeat=102;
    public static final int WebSocket_Type_Push=103;

    public static final int Event_Type_Push=1;
    public static final int Event_Type_MsgNotify=2;
}
