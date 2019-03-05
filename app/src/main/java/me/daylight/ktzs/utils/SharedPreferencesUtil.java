package me.daylight.ktzs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesUtil {
    private static SharedPreferences getSP(Context context, String table) {
        return context.getSharedPreferences(table, Context.MODE_PRIVATE);
    }

    public static void putValue(Context context, String table, String key, int value) {
        getSP(context, table).edit().putInt(key, value).apply();
    }

    public static void putValue(Context context, String table, String key, String value) {
        getSP(context, table).edit().putString(key, value).apply();
    }

    public static void putValue(Context context, String table, String key, boolean value) {
        getSP(context, table).edit().putBoolean(key, value).apply();
    }

    public static void putValue(Context context, String table, String key, Set<String> value) {
        getSP(context, table).edit().putStringSet(key, value).apply();
    }

    public static int getInt(Context context, String table, String key) {
        return getSP(context, table).getInt(key, 0);
    }

    public static String getString(Context context, String table, String kye) {
        return getSP(context, table).getString(kye, "");
    }

    public static boolean getBoolean(Context context, String table, String key) {
        return getSP(context, table).getBoolean(key, false);
    }

    public static Set<String> getSet(Context context,String table,String key){
        return getSP(context,table).getStringSet(key,null);
    }
}
