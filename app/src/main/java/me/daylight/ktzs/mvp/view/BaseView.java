package me.daylight.ktzs.mvp.view;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

public interface BaseView {
    QMUIFragmentActivity getCurContext();

    void showErrorMsg(String errorMsg);

    void showInfo(int type,String msg);
}
