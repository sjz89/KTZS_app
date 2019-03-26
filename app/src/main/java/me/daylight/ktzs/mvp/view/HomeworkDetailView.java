package me.daylight.ktzs.mvp.view;

import java.util.Map;

import me.daylight.ktzs.entity.Homework;

public interface HomeworkDetailView extends BaseView {
    void initItemView(Map<String,Object> homework);
}
