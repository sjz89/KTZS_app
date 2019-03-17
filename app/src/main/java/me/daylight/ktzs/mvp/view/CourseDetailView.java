package me.daylight.ktzs.mvp.view;

import java.util.List;
import java.util.Map;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Notice;

/**
 * @author Daylight
 * @date 2019/03/12 22:55
 */
public interface CourseDetailView extends BaseView {
    void initItemView();

    void initLatestRecord(Map<String,String> map);

    void initLatestNotice(Notice notice);

    void initRecyclerView(List<CommonData> userList);

    void hideLoading();

    void showEmptyInfo(String info);

    void hideRefresh();
}
