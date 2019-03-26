package me.daylight.ktzs.mvp.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.NoticeModel;
import me.daylight.ktzs.mvp.view.NoticeView;
import me.daylight.ktzs.utils.DateUtil;
import me.daylight.ktzs.utils.DialogUtil;

/**
 * @author Daylight
 * @date 2019/03/12 00:12
 */
public class NoticePresenter extends BasePresenter<NoticeView, NoticeModel> {
    public void initNotices(Course course){
        OnHttpCallBack<RetResult<List<Notice>>> callBack=new OnHttpCallBack<RetResult<List<Notice>>>() {
            @Override
            public void onSuccess(RetResult<List<Notice>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Notice notice:listRetResult.getData()){
                    CommonData commonData;
                    if (course==null) {
                        commonData = new CommonData(null, notice.getCourseName(), DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss", new Date(notice.getTime())), notice.getId());
                        commonData.setCustomText(notice.getContent());
                    }else{
                        commonData=new CommonData(null,notice.getContent(),null,notice.getId());
                        commonData.setCustomText(DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss", new Date(notice.getTime())));
                    }
                    commonDataList.add(commonData);
                }
                getView().initRecyclerView(commonDataList);
                getView().hideLoading();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        };
        if (course!=null)
            getModel().getNoticesByCourse(course.getId(),callBack);
        else
            getModel().getAllNotices(callBack);
    }

    public void swipeToRefresh(Course course){
        OnHttpCallBack<RetResult<List<Notice>>> callBack=new OnHttpCallBack<RetResult<List<Notice>>>() {
            @Override
            public void onSuccess(RetResult<List<Notice>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Notice notice:listRetResult.getData()){
                    CommonData commonData;
                    if (course==null) {
                        commonData = new CommonData(null, notice.getCourseName(), DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss", new Date(notice.getTime())), notice.getId());
                        commonData.setCustomText(notice.getContent());
                    }else{
                        commonData=new CommonData(null,notice.getContent(),null,notice.getId());
                        commonData.setCustomText(DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss", new Date(notice.getTime())));
                    }
                    commonDataList.add(commonData);
                }
                getView().initRecyclerView(commonDataList);
                getView().hideRefresh();
                DialogUtil.showToast(getView().getCurContext(),"刷新成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        };
        if (course==null)
            getModel().getAllNotices(callBack);
        else
            getModel().getNoticesByCourse(course.getId(),callBack);
    }
}
