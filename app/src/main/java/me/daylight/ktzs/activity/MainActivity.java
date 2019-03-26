package me.daylight.ktzs.activity;

import android.app.Notification;
import android.app.NotificationManager;
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
import me.daylight.ktzs.app.WebSocketService;
import me.daylight.ktzs.entity.EventMsg;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.mvp.view.fragment.BaseFragment;
import me.daylight.ktzs.mvp.view.fragment.MainFragment;
import me.daylight.ktzs.utils.GlobalField;


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
            startService(new Intent(this, WebSocketService.class));
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void sendNotification(EventMsg eventMsg){
        if (eventMsg.getEventChannel()== GlobalField.Event_Channel_Notice) {
            Notice notice=(Notice) eventMsg.getData();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "KTZS");
            mBuilder.setContentTitle(notice.getCourseName())
                    .setContentText(notice.getContent())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(notice.getTime())
                    .setSmallIcon(getApplicationInfo().icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_ketang));
            Objects.requireNonNull(notificationManager).notify( notice.getId().intValue(), mBuilder.build());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,WebSocketService.class));
        EventBus.getDefault().unregister(this);
    }
}
