package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;

public interface HomeworkFileView extends BaseView {
    void initRecyclerView(List<CommonData> files);

    void hideRefresh();

    void hideLoading();

    void showEmptyInfo(String info);
}
