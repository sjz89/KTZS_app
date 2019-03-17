package me.daylight.ktzs.mvp.view;

import me.daylight.ktzs.entity.User;

/**
 * @author Daylight
 * @date 2019/03/16 19:14
 */
public interface UserInfoView extends BaseView {
    void setInfo(User user);
}
