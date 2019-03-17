package me.daylight.ktzs.mvp.presenter;

import java.util.List;

import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.CourseModel;
import me.daylight.ktzs.mvp.view.CourseView;

/**
 * @author Daylight
 * @date 2019/03/08 15:37
 */
public class CoursePresenter extends BasePresenter<CourseView, CourseModel> {
    public void getMyCourses(){
        getModel().getMyCourses(new OnHttpCallBack<RetResult<List<Course>>>() {
            @Override
            public void onSuccess(RetResult<List<Course>> listRetResult) {
                getView().addItems(listRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

}
