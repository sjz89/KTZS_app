package me.daylight.ktzs.mvp.presenter;

import android.os.CountDownTimer;

import me.daylight.ktzs.entity.SignInState;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.StartSignInModel;
import me.daylight.ktzs.mvp.view.StartSignInView;

/**
 * @author Daylight
 * @date 2019/03/08 16:25
 */
public class StartSignInPresenter extends BasePresenter<StartSignInView, StartSignInModel> {
    private CountDownTimer timer;

    public void initState(){
        getModel().getAttendanceNow(new OnHttpCallBack<RetResult<SignInState>>() {
            @Override
            public void onSuccess(RetResult<SignInState> signInStateRetResult) {
                getView().initViews(signInStateRetResult.getData().getUniqueId());
                startCountDown(signInStateRetResult.getData().getRemainTime());
                getView().createQRCode(signInStateRetResult.getData().getUniqueId());
                getView().showCount(signInStateRetResult.getData().getCount());
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void stopSignIn(String uniqueId){
        getModel().stopSignIn(uniqueId, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                getView().changeBtn();
                timer.onFinish();
                timer.cancel();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    private void startCountDown(Long remainTime){
        if (timer==null){
            timer=new CountDownTimer(remainTime,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    getView().showRemainTime(getGapTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    getView().showRemainTime("签到已结束");
                    getView().changeBtn();
                }
            };
        }
        timer.start();
    }

    private String getGapTime(long time) {
        long minutes = time / (1000 * 60);
        long seconds = (time - minutes * (1000 * 60)) / 1000;
        String diffTime;
        if (seconds < 10) {
            diffTime = minutes + ":0" + seconds;
        } else {
            diffTime = minutes + ":" + seconds;
        }
        return diffTime;
    }

    public void stopCountDown(){
        timer.cancel();
        timer=null;
    }
}
