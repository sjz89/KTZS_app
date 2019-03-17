package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.LeaveModel;
import me.daylight.ktzs.mvp.view.LeaveView;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public class LeavePresenter extends BasePresenter<LeaveView, LeaveModel> {
    private List<Course> courses;
    public void initItemView(){
        getModel().initCourseList(new OnHttpCallBack<RetResult<List<Course>>>() {
            @Override
            public void onSuccess(RetResult<List<Course>> courseRetResult) {
                courses=courseRetResult.getData();
                List<String> courseList=new ArrayList<>();
                for (Course course:courses)
                    courseList.add(course.getName());
                getView().initItemView(courseList);
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void submitLeaveNote(int index,String reason){
        getModel().submit(courses.get(index).getId(), reason, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"请假申请提交成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
