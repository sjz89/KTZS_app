package me.daylight.ktzs.mvp.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.daylight.ktzs.entity.CommonData;
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
    public void initNotices(){
        getModel().getAllNotices(new OnHttpCallBack<RetResult<List<Notice>>>() {
            @Override
            public void onSuccess(RetResult<List<Notice>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Notice notice:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,notice.getCourseName(),DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss",new Date(notice.getTime())),notice.getId());
                    commonData.setCustomText(notice.getContent());
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
        });
    }

    public void swipeToRefresh(){
        getModel().getAllNotices(new OnHttpCallBack<RetResult<List<Notice>>>() {
            @Override
            public void onSuccess(RetResult<List<Notice>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Notice notice:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,notice.getCourseName(),DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss",new Date(notice.getTime())),notice.getId());
                    commonData.setCustomText(notice.getContent());
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
        });
    }
}
