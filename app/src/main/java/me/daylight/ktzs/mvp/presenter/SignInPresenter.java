package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import me.daylight.ktzs.entity.AttendanceRecord;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.SignInModel;
import me.daylight.ktzs.mvp.view.SignInView;
import me.daylight.ktzs.utils.LocationUtil;

/**
 * @author Daylight
 * @date 2019/03/07 17:17
 */
public class SignInPresenter extends BasePresenter<SignInView, SignInModel> {

    private Double[] locations;

    public void signIn(String signInCode){
        if (signInCode.equals("")) {
            getView().showErrorMsg("请输入签到码");
            return;
        }
        getModel().signIn(signInCode, locations[0], locations[1], new OnHttpCallBack<RetResult<AttendanceRecord>>() {
            @Override
            public void onSuccess(RetResult<AttendanceRecord> attendanceRecordRetResult) {
                getView().emptyEdit();
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"签到成功");
                getView().addLatestRecord(attendanceRecordRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void initRecords(){
        getModel().initRecords(new OnHttpCallBack<RetResult<List<AttendanceRecord>>>() {
            @Override
            public void onSuccess(RetResult<List<AttendanceRecord>> listRetResult) {
                getView().initRecord(listRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void getLocation(){
        locations=new Double[2];
        LocationUtil.getLocation(getView().getCurContext(), location -> {
            getView().showLocation(location);
            locations[0]=location.getLongitude();
            locations[1]=location.getLatitude();
        });
    }
}
