package me.daylight.ktzs.mvp.model;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.GlobalField;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public class LeaveModel extends BaseModel {
    public void initCourseList(OnHttpCallBack<RetResult<List<Course>>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getMyCourses(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<List<Course>>>(callBack) {});
    }

    public void submit(Long courseId,String reason,OnHttpCallBack<RetResult> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .submitLeaveNote(courseId, reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<>(callBack));
    }
}
