package me.daylight.ktzs.mvp.model;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;

/**
 * @author Daylight
 * @date 2019/03/12 22:55
 */
public class CourseDetailModel extends BaseModel {
    public void push(Map<String,Object> notice, OnHttpCallBack<RetResult> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .push(notice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }

    public void getLatestRecord(Long courseId, OnHttpCallBack<RetResult<Map<String,String>>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getLatestRecord(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }

    public void getLatestNotice(Long courseId, OnHttpCallBack<RetResult<Notice>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getLatestNotice(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }

    public void getStudentsByCourse(Long courseId, OnHttpCallBack<RetResult<List<User>>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getStudentsByCourse(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }
}
