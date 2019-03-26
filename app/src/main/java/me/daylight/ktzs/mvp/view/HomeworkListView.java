package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;

public interface HomeworkListView extends BaseView {
    void initRecyclerView(List<CommonData> homeworks);

    void hideRefresh();

    void hideLoading();

    void showEmptyInfo(String info);
}
