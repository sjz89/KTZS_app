package me.daylight.ktzs.mvp.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.TeacherRecordModel;
import me.daylight.ktzs.mvp.view.TeacherRecordView;
import me.daylight.ktzs.utils.DialogUtil;

public class TeacherRecordPresenter extends BasePresenter<TeacherRecordView, TeacherRecordModel> {
    public void swipeToRefresh(){
        getModel().getRecordList(new OnHttpCallBack<RetResult<List<Map<String,Object>>>>() {
            @Override
            public void onSuccess(RetResult<List<Map<String,Object>>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Map<String,Object> record:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,(String)record.get("courseName"),(String)record.get("uniqueId"),
                            Long.parseLong((String) Objects.requireNonNull(record.get("uniqueId"))));
                    commonData.setCustomText((String)record.get("count"));
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

    public void initRecyclerView(){
        getModel().getRecordList(new OnHttpCallBack<RetResult<List<Map<String,Object>>>>() {
            @Override
            public void onSuccess(RetResult<List<Map<String,Object>>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Map<String,Object> record:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,(String)record.get("courseName"),(String)record.get("uniqueId"),
                            Long.parseLong((String) Objects.requireNonNull(record.get("uniqueId"))));
                    commonData.setCustomText((String)record.get("count"));
                    commonDataList.add(commonData);
                }
                getView().hideLoading();
                getView().initRecyclerView(commonDataList);
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        });
    }
}
