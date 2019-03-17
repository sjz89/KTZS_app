package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.CourseDetailModel;
import me.daylight.ktzs.mvp.view.CourseDetailView;

/**
 * @author Daylight
 * @date 2019/03/12 22:55
 */
public class CourseDetailPresenter extends BasePresenter<CourseDetailView, CourseDetailModel> {
    public void push(Map<String,Object> notice){
        getModel().push(notice, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"发送成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void initDetailInfo(Long courseId){
        getView().initItemView();

        getModel().getLatestNotice(courseId, new OnHttpCallBack<RetResult<Notice>>() {
            @Override
            public void onSuccess(RetResult<Notice> noticeRetResult) {
                getView().initLatestNotice(noticeRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });

        getModel().getLatestRecord(courseId, new OnHttpCallBack<RetResult<Map<String, String>>>() {
            @Override
            public void onSuccess(RetResult<Map<String, String>> mapRetResult) {
                getView().initLatestRecord(mapRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void initMembers(Long courseId){
        getModel().getStudentsByCourse(courseId, new OnHttpCallBack<RetResult<List<User>>>() {
            @Override
            public void onSuccess(RetResult<List<User>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (User user:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,user.getName(),user.getIdNumber(),user.getId());
                    commonDataList.add(commonData);
                }
                getView().initRecyclerView(commonDataList);
                getView().hideLoading();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void swipeToRefresh(Long courseId){
        getModel().getStudentsByCourse(courseId, new OnHttpCallBack<RetResult<List<User>>>() {
            @Override
            public void onSuccess(RetResult<List<User>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (User user:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,user.getName(),user.getIdNumber(),user.getId());
                    commonDataList.add(commonData);
                }
                getView().initRecyclerView(commonDataList);
                getView().hideRefresh();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        });
    }
}
