package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Notice;

/**
 * @author Daylight
 * @date 2019/03/12 00:12
 */
public interface NoticeView extends BaseView {
    void initRecyclerView(List<CommonData> notices);

    void hideRefresh();

    void hideLoading();

    void showEmptyInfo(String info);
}
