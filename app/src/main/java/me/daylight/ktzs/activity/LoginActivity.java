package me.daylight.ktzs.activity;

import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import me.daylight.ktzs.R;
import me.daylight.ktzs.mvp.view.fragment.BaseFragment;
import me.daylight.ktzs.mvp.view.fragment.LoginFragment;

public class LoginActivity extends QMUIFragmentActivity {
    @Override
    protected int getContextViewId() {
        return R.id.login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            BaseFragment fragment=new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(),fragment,fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
