package me.daylight.ktzs.mvp.view;

/**
 * @author Daylight
 * @date 2019/03/08 16:26
 */
public interface StartSignInView extends BaseView {
    void initViews(String uniqueId);

    void createQRCode(String uniqueId);

    void showRemainTime(String time);

    void showCount(int count);

    void changeBtn();
}
