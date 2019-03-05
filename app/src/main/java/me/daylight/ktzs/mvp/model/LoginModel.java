package me.daylight.ktzs.mvp.model;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.model.entity.User;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.http.HttpFeedBackUtil;

public class LoginModel extends BaseModel {
    @Override
    public void init() {

    }

    public void login(String account, String password, OnHttpCallBack<RetResult> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .login(account,password)
                .flatMap((Function<RetResult, Observable<RetResult<User>>>) retResult->{
                    HttpFeedBackUtil.handleRetResult(retResult,callBack);
                    if (retResult.getCode()==RetResult.RetCode.SUCCESS.code){
                        return RetrofitUtils.newInstance(GlobalField.url).create(HttpContract.class).getSelfInfo();
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(userRetResult -> {
                    //todo 存数据库
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<User>>(callBack) {});

    }
}
