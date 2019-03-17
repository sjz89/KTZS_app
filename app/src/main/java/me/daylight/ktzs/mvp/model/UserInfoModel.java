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

/**
 * @author Daylight
 * @date 2019/03/16 19:14
 */
public class UserInfoModel extends BaseModel {
    public void getUserInfo(String idNumber, OnHttpCallBack<RetResult<User>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getUserInfo(idNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }

    public void getTeacherInfo(Long courseId,OnHttpCallBack<RetResult<User>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getTeacherInfo(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }
}
