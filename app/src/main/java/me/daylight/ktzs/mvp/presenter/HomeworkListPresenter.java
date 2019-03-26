package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Homework;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.HomeworkListModel;
import me.daylight.ktzs.mvp.view.HomeworkListView;
import me.daylight.ktzs.utils.DateUtil;
import me.daylight.ktzs.utils.DialogUtil;

public class HomeworkListPresenter extends BasePresenter<HomeworkListView, HomeworkListModel> {
    public void swipeToRefresh(){
        getModel().getHomeworkByCourse( new OnHttpCallBack<RetResult<List<Homework>>>() {
            @Override
            public void onSuccess(RetResult<List<Homework>> listRetResult) {
                getView().initRecyclerView(transferToCommonDataList(listRetResult.getData()));
                getView().hideRefresh();
                DialogUtil.showToast(getView().getCurContext(),"刷新成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void initHomeworkList(){
        getModel().getHomeworkByCourse( new OnHttpCallBack<RetResult<List<Homework>>>() {
            @Override
            public void onSuccess(RetResult<List<Homework>> listRetResult) {
                getView().initRecyclerView(transferToCommonDataList(listRetResult.getData()));
                getView().hideLoading();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        });
    }

    private List<CommonData> transferToCommonDataList(List<Homework> homeworkList){
        List<CommonData> commonDataList=new ArrayList<>();
        for (Homework homework:homeworkList){
            CommonData commonData=new CommonData(null,homework.getName(),(String)homework.getCourse().get("name"),homework.getId());
            if (DateUtil.strToDate("yyyy-MM-dd",homework.getEndTime()).getTime()<new Date().getTime())
                commonData.setCustomText("已结束");
            else
                commonData.setCustomText("截止时间："+homework.getEndTime());
            commonDataList.add(commonData);
        }
        return commonDataList;
    }

    public void publishHomework(Long courseId,String title,String content,Long endTime){
        getModel().publishHomework(courseId, title,content, endTime, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"发布成功");
                initHomeworkList();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
