package me.daylight.ktzs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.R;
import me.daylight.ktzs.app.GlideApp;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.IMEIUtil;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    public static int PERMISSION_REQ = 0x123456;

    private String[] mPermission = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };

    private List<String> mRequestPermission = new ArrayList<>();

    @BindView(R.id.splash_View)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        if ("".equals(SharedPreferencesUtil.getString(this,GlobalField.SETTING,GlobalField.URL)))
            SharedPreferencesUtil.putValue(this,GlobalField.SETTING,GlobalField.URL,"https://ktzs.suazen.com");

        if (isDayTime())
            GlideApp.with(this).load(R.mipmap.good_morning_img).centerCrop().into(imageView);
        else
            GlideApp.with(this).load(R.mipmap.good_night_img).centerCrop().into(imageView);

        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_splash));

        for (String one : mPermission) {
            if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                mRequestPermission.add(one);
            }
        }
        if (!mRequestPermission.isEmpty()) {
            this.requestPermissions(mRequestPermission.toArray(new String[0]), PERMISSION_REQ);
            return;
        }
        startActivity();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
            startActivity();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQ) {
            if (resultCode == 0) {
                this.finish();
            }
        }
    }

    private void startActivity(){
        String account= SharedPreferencesUtil.getString(this, GlobalField.USER,"idNumber");
        String password=SharedPreferencesUtil.getString(this,GlobalField.USER,"password");
        new Handler().postDelayed(()->{
            if (account.equals("")||password.equals("")){
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }else{
                login(account, password, new OnHttpCallBack<RetResult>() {
                    @Override
                    public void onSuccess(RetResult retResult) {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        SharedPreferencesUtil.putValue(getApplicationContext(), GlobalField.USER, "password", "");
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        },2000);
        new Handler().postDelayed(()->{
            if (!this.isFinishing()) {
                SharedPreferencesUtil.putValue(getApplicationContext(), GlobalField.USER, "password", "");
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },8000);
    }

    private void login(String account,String password,OnHttpCallBack<RetResult> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .login(IMEIUtil.getIMEI(this),account,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult>(callBack));
    }

    private boolean isDayTime(){
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        if (hour>6&&hour<18)
            return true;
        if (hour==6&&minute>=30)
            return true;
        return hour == 18 && minute <= 30;
    }
}
