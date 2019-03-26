package me.daylight.ktzs.mvp.presenter;

import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.mvp.model.MainModel;
import me.daylight.ktzs.mvp.view.MainView;
import me.daylight.ktzs.utils.LocationUtil;

/**
 * @author Daylight
 * @date 2019/03/06 18:51
 */
public class MainPresenter extends BasePresenter<MainView, MainModel> {
    public void initInfoView(){
        getModel().initUserInfo(new OnHttpCallBack<RetResult<User>>() {
            @Override
            public void onSuccess(RetResult<User> userRetResult) {
                getView().initMenu(userRetResult.getData());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void getAttendanceNow(){
        getModel().checkSignInProgress(new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().toStartSignInFragment();
            }

            @Override
            public void onFailed(String errorMsg) {
                if (errorMsg.equals("none"))
                    getView().showStartSignInDialog();
                else
                    getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void startSignIn(Long courseId,int limitMin){
        final Double[] locations=new Double[2];
        LocationUtil.getLocation(getView().getCurContext(),location -> {
            locations[0]=location.getLongitude();
            locations[1]=location.getLatitude();
        });
        getModel().startSignIn(courseId, limitMin, locations[0], locations[1], new OnHttpCallBack<RetResult<String>>() {
            @Override
            public void onSuccess(RetResult<String> stringRetResult) {
                getView().toStartSignInFragment();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void logout(){
        getModel().logout();
    }
}
