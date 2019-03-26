package me.daylight.ktzs.mvp.presenter;

import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.LoginModel;
import me.daylight.ktzs.mvp.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView, LoginModel> {
    public void login(String account,String password){
        getView().showProgress();
        new Handler().postDelayed(()-> getModel().login(account, password, new OnHttpCallBack<RetResult<String>>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().toMain();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().hideProgress();
                getView().showErrorMsg(errorMsg);
                if (errorMsg.equals("Illegal Device"))
                    new QMUIDialog.MessageDialogBuilder(getView().getCurContext())
                            .setMessage("登陆设备不合法，是否申请更换设备")
                            .addAction("取消",((dialog, index) -> dialog.dismiss()))
                            .addAction("确定",((dialog, index) -> {
                                dialog.dismiss();
                                deviceReplace(account);
                            }))
                            .show();
            }
        }),2000);
    }

    public void setAccount(){
        getView().setAccount(getModel().loadAccount());
    }

    private void deviceReplace(String idNumber){
        getModel().deviceReplace(idNumber,new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"成功发送请求，等待审核中");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
