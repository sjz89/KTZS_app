package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.InfoModel;
import me.daylight.ktzs.mvp.view.InfoView;

/**
 * @author Daylight
 * @date 2019/03/06 22:48
 */
public class InfoPresenter extends BasePresenter<InfoView, InfoModel> {
    public void loadInfo(){
        getView().setInfo(getModel().loadUser());
    }

    public void saveInfo(String name,String phone){
        getModel().saveInfo(name, phone, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"信息更改成功");
                getView().editable(false);
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }
}
