package me.daylight.ktzs.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.app.GlideApp;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_View)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        GlideApp.with(this).load(R.drawable.splash).centerCrop().into(imageView);
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_splash));


    }
}
