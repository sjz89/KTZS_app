package me.daylight.ktzs.mvp.view;


import me.daylight.ktzs.entity.User;

/**
 * @author Daylight
 * @date 2019/03/06 18:52
 */
public interface MainView extends BaseView {
    void initMenu(User user);

    void showStartSignInDialog();

    void toStartSignInFragment();

}
