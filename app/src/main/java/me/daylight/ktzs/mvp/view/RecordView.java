package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;


/**
 * @author Daylight
 * @date 2019/03/10 01:11
 */
public interface RecordView extends BaseView {
    void hideRefresh();

    void initRecyclerView(List<CommonData> noticeList);

    void hideLoading();

    void showEmptyInfo(String info);

}
