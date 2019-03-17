package me.daylight.ktzs.mvp.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

public class LoginModel extends BaseModel {
    @Override
    public void init() {

    }

    public String loadAccount(){
        return SharedPreferencesUtil.getString(getContext(),GlobalField.USER,"idNumber");
    }

    public void login(String account, String password, OnHttpCallBack<RetResult<String>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .login(account,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<String>>(callBack) {
                    @Override
                    public void onNext(RetResult<String> retResult) {
                        super.onNext(retResult);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"idNumber",account);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"password",password);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"role",retResult.getData());
                    }
                });
    }
}
