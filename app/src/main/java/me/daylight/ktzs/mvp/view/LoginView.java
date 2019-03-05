package me.daylight.ktzs.mvp.view;

import android.widget.ArrayAdapter;

public interface LoginView extends BaseView {
    void showProgress();//可以显示进度条

    void hideProgress();//可以隐藏进度条

    void showInfo(String info);//提示用户,提升友好交互

    void showErrorMsg(String msg);//发生错误就显示错误信息

    void toMain();//跳转到主页面

    void setAccount(String phone);
}
