package me.daylight.ktzs.mvp.presenter;

import java.util.Map;

import me.daylight.ktzs.entity.Homework;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.HomeworkDetailModel;
import me.daylight.ktzs.mvp.view.HomeworkDetailView;

public class HomeworkDetailPresenter extends BasePresenter<HomeworkDetailView, HomeworkDetailModel> {
    public void initItemView(Long homeworkId){
        getModel().getDetail(homeworkId, new OnHttpCallBack<RetResult<Map<String,Object>>>() {
            @Override
            public void onSuccess(RetResult<Map<String,Object>> homeworkRetResult) {
                getView().initItemView(homeworkRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
