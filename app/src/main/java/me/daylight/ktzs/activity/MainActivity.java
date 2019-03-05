package me.daylight.ktzs.activity;

import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import me.daylight.ktzs.R;
import me.daylight.ktzs.mvp.view.fragment.BaseFragment;
import me.daylight.ktzs.mvp.view.fragment.MainFragment;


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
        }
    }

}
