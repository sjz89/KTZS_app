package me.daylight.ktzs.mvp.view;

import me.daylight.ktzs.entity.User;

/**
 * @author Daylight
 * @date 2019/03/06 22:47
 */
public interface InfoView extends BaseView {
    void setInfo(User user);

    void editable(boolean b);

}
