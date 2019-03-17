package me.daylight.ktzs.mvp.presenter;

import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.UserInfoModel;
import me.daylight.ktzs.mvp.view.UserInfoView;

/**
 * @author Daylight
 * @date 2019/03/16 19:14
 */
public class UserInfoPresenter extends BasePresenter<UserInfoView, UserInfoModel> {
    private OnHttpCallBack<RetResult<User>> initUserInfo=new OnHttpCallBack<RetResult<User>>() {
        @Override
        public void onSuccess(RetResult<User> userRetResult) {
            getView().setInfo(userRetResult.getData());
        }

        @Override
        public void onFailed(String errorMsg) {
            getView().showErrorMsg(errorMsg);
        }
    };

    public void initUserInfo(String idNumber){
        getModel().getUserInfo(idNumber,initUserInfo);
    }

    public void initTeacherInfo(Long courseId){
        getModel().getTeacherInfo(courseId, initUserInfo);
    }
}
