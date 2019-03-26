package me.daylight.ktzs.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import me.daylight.ktzs.http.WsManager;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

public class WebSocketService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (SharedPreferencesUtil.getString(this, GlobalField.USER,"role").equals("student"))
            WsManager.getInstance().init(WsManager.Channel_Notice);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WsManager.getInstance().disconnect(WsManager.Channel_Notice);
        WsManager.getInstance().destroy();
    }
}
