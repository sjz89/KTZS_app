package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.Leave;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public interface LeaveView extends BaseView {
    void initRecyclerView(List<Leave> notices);

    void hideRefresh();

    void hideLoading();

    void showEmptyInfo(String info);
}
