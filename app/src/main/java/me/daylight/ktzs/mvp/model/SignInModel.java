package me.daylight.ktzs.mvp.model;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.entity.AttendanceRecord;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;

/**
 * @author Daylight
 * @date 2019/03/07 17:17
 */
public class SignInModel extends BaseModel {
    public void signIn(String uniqueId, double x, double y, OnHttpCallBack<RetResult<AttendanceRecord>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .signIn(uniqueId, x, y)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult>(callBack) {});
    }

    public void initRecords(OnHttpCallBack<RetResult<List<AttendanceRecord>>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getMyAttendance(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<List<AttendanceRecord>>>(callBack) {});
    }
}
