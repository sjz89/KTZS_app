package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import me.daylight.ktzs.entity.Leave;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.LeaveModel;
import me.daylight.ktzs.mvp.view.LeaveView;
import me.daylight.ktzs.utils.DialogUtil;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public class LeavePresenter extends BasePresenter<LeaveView, LeaveModel> {

    public void submitLeaveNote(Long startDate,Long endDate,String reason){
        getModel().submit(startDate,endDate, reason, new OnHttpCallBack<RetResult>() {
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

    public void initRecyclerView(){
        getModel().initLeaveRecordList(new OnHttpCallBack<RetResult<List<Leave>>>() {
            @Override
            public void onSuccess(RetResult<List<Leave>> listRetResult) {
                getView().initRecyclerView(listRetResult.getData());
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
        getModel().initLeaveRecordList(new OnHttpCallBack<RetResult<List<Leave>>>() {
            @Override
            public void onSuccess(RetResult<List<Leave>> listRetResult) {
                getView().initRecyclerView(listRetResult.getData());
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
