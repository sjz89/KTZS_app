package me.daylight.ktzs.mvp.presenter;

import android.os.Handler;

import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.LoginModel;
import me.daylight.ktzs.mvp.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView, LoginModel> {
    public void login(String account,String password){
        getView().showProgress();
        new Handler().postDelayed(()-> getModel().login(account, password, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().toMain();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().hideProgress();
                getView().showErrorMsg(errorMsg);
            }
        }),2000);
    }
}
