package me.daylight.ktzs.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import androidx.core.app.NotificationCompat;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.EventMsg;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.http.WsManager;
import me.daylight.ktzs.mvp.view.fragment.BaseFragment;
import me.daylight.ktzs.mvp.view.fragment.MainFragment;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;


public class MainActivity extends QMUIFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){

            BaseFragment fragment=new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(),fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
            EventBus.getDefault().register(this);
            if (SharedPreferencesUtil.getString(this,GlobalField.USER,"role").equals("student"))
                WsManager.getInstance().init(WsManager.Channel_Notice);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void sendNotification(EventMsg eventMsg){
        if (eventMsg.getEventChannel()== GlobalField.Event_Channel_Notice) {
            Notice notice=(Notice) eventMsg.getData();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Talk");
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("type", 1);
//            intent.putExtra("phone", notice.getSendPhone());
//            PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentTitle(notice.getCourseName())
                    .setContentText(notice.getContent())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(notice.getTime())
                    .setSmallIcon(getApplicationInfo().icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_ketang));
//                    .setContentIntent(mainPendingIntent);
            Objects.requireNonNull(notificationManager).notify( notice.getId().intValue(), mBuilder.build());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        WsManager.getInstance().disconnect(WsManager.Channel_Notice);
        WsManager.getInstance().destroy();
    }
}
