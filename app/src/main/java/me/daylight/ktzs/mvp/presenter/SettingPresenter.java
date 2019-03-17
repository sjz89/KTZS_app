package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.SettingModel;
import me.daylight.ktzs.mvp.view.SettingView;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;

/**
 * @author Daylight
 * @date 2019/03/11 23:21
 */
public class SettingPresenter extends BasePresenter<SettingView, SettingModel> {
    public void logout(){
        getModel().logout(new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                SharedPreferencesUtil.putValue(getView().getCurContext(),GlobalField.USER,"password","");
                getView().toLogin();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void changePwd(String oldPwd,String password){
        getModel().changePwd(oldPwd,password, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                SharedPreferencesUtil.putValue(getView().getCurContext(), GlobalField.USER,"password",password);
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"密码更改成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
