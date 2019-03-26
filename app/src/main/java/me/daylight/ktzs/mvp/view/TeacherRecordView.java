package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;

public interface TeacherRecordView extends BaseView {
    void hideRefresh();

    void initRecyclerView(List<CommonData> noticeList);

    void hideLoading();

    void showEmptyInfo(String info);
}
