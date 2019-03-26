package me.daylight.ktzs.mvp.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.SignInDetailModel;
import me.daylight.ktzs.mvp.view.SignInDetailView;
import me.daylight.ktzs.utils.DialogUtil;

public class SignInDetailPresenter extends BasePresenter<SignInDetailView, SignInDetailModel> {
    public void initStudentsList(){
        getModel().getSignInList(new OnHttpCallBack<RetResult<List<Map<String, Object>>>>() {
            @Override
            public void onSuccess(RetResult<List<Map<String, Object>>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Map<String,Object> map:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,(String)map.get("name"),(String)map.get("idNumber"),((Double) Objects.requireNonNull(map.get("id"))).longValue());
                    commonData.setCustomText((String)map.get("time"));
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
        getModel().getSignInList(new OnHttpCallBack<RetResult<List<Map<String, Object>>>>() {
            @Override
            public void onSuccess(RetResult<List<Map<String, Object>>> listRetResult) {
                List<CommonData> commonDataList=new ArrayList<>();
                for (Map<String,Object> map:listRetResult.getData()){
                    CommonData commonData=new CommonData(null,(String)map.get("name"),(String)map.get("idNumber"),(Long)map.get("id"));
                    commonData.setCustomText((String)map.get("time"));
                    commonDataList.add(commonData);
                }
                getView().initRecyclerView(commonDataList);
                DialogUtil.showToast(getView().getCurContext(),"刷新成功");
                getView().hideRefresh();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().hideRefresh();
            }
        });
    }
}
