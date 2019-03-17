package me.daylight.ktzs.mvp.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

/**
 * @author Daylight
 * @date 2019/03/06 22:48
 */
public class InfoModel extends BaseModel {
    public User loadUser(){
        User user=new User();
        user.setIdNumber(SharedPreferencesUtil.getString(getContext(), GlobalField.USER,"idNumber"));
        user.setName(SharedPreferencesUtil.getString(getContext(),GlobalField.USER,"name"));
        user.setPhone(SharedPreferencesUtil.getString(getContext(),GlobalField.USER,"phone"));
        user.setRole(SharedPreferencesUtil.getString(getContext(),GlobalField.USER,"role"));
        return user;
    }

    public void saveInfo(String name, String phone, OnHttpCallBack<RetResult> callBack){
        User user=new User();
        user.setName(name);
        user.setPhone(phone);
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .changeInfo(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult>(callBack) {
                    @Override
                    public void onNext(RetResult retResult) {
                        super.onNext(retResult);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"name",name);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"phone",phone);
                    }
                });
    }
}
