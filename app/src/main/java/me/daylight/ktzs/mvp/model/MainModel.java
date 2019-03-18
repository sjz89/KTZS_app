package me.daylight.ktzs.mvp.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

/**
 * @author Daylight
 * @date 2019/03/06 18:52
 */
public class MainModel extends BaseModel {

    public void initUserInfo(OnHttpCallBack<RetResult<User>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getSelfInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<User>>(callBack) {
                    @Override
                    public void onNext(RetResult<User> userRetResult) {
                        super.onNext(userRetResult);
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"id",userRetResult.getData().getId().intValue());
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"idNumber",userRetResult.getData().getIdNumber());
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"name",userRetResult.getData().getName());
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"phone",userRetResult.getData().getPhone());
                        SharedPreferencesUtil.putValue(getContext(),GlobalField.USER,"role",userRetResult.getData().getRole());
                    }
                });
    }

    public void checkSignInProgress(OnHttpCallBack<RetResult> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .checkProgress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult>(callBack) {});
    }

    public void startSignIn(Long courseId,int limitMin,Double x,Double y,OnHttpCallBack<RetResult<String>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .startSignIn(courseId, limitMin, x, y)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<String>>(callBack) {});
    }

}
