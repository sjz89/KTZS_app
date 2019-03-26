package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.CommonData;

public interface SignInDetailView extends BaseView {
    void initRecyclerView(List<CommonData> notices);

    void hideRefresh();

    void hideLoading();

    void showEmptyInfo(String info);
}
