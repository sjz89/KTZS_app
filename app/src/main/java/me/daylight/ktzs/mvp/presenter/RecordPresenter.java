package me.daylight.ktzs.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import me.daylight.ktzs.entity.AttendanceRecord;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.RecordModel;
import me.daylight.ktzs.mvp.view.RecordView;
import me.daylight.ktzs.utils.DialogUtil;

/**
 * @author Daylight
 * @date 2019/03/10 01:11
 */
public class RecordPresenter extends BasePresenter<RecordView, RecordModel> {
    public void swipeToRefresh(String uniqueId){
        OnHttpCallBack<RetResult<List<AttendanceRecord>>> callBack=new OnHttpCallBack<RetResult<List<AttendanceRecord>>>() {
            @Override
            public void onSuccess(RetResult<List<AttendanceRecord>> listRetResult) {
                List<CommonData> commonDataList=initCommonDataList(listRetResult.getData());
                getView().initRecyclerView(commonDataList);
                getView().hideRefresh();
                DialogUtil.showToast(getView().getCurContext(),"刷新成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        };

        if (uniqueId==null)
            getModel().getAllRecords(callBack);
        else
            getModel().getRecordByUniqueId(uniqueId,callBack);
    }

    public void initRecyclerView(String uniqueId){
        OnHttpCallBack<RetResult<List<AttendanceRecord>>> callBack=new OnHttpCallBack<RetResult<List<AttendanceRecord>>>() {
            @Override
            public void onSuccess(RetResult<List<AttendanceRecord>> listRetResult) {
                List<CommonData> commonDataList=initCommonDataList(listRetResult.getData());
                getView().hideLoading();
                getView().initRecyclerView(commonDataList);
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        };
        if (uniqueId==null)
            getModel().getAllRecords(callBack);
        else
            getModel().getRecordByUniqueId(uniqueId,callBack);
    }

    private List<CommonData> initCommonDataList(List<AttendanceRecord> records){
        List<CommonData> commonDataList=new ArrayList<>();
        for (AttendanceRecord record:records){
            CommonData commonData=new CommonData(null,record.getCourseName()!=null?record.getCourseName():record.getStudentName(),record.getTime(),record.getId());
            commonData.setCustomText(record.getState()==1?"已签到":"未签到");
            commonDataList.add(commonData);
        }
        return commonDataList;
    }
}
