package me.daylight.ktzs.mvp.view;

import java.util.List;

import me.daylight.ktzs.entity.Course;

/**
 * @author Daylight
 * @date 2019/03/08 15:37
 */
public interface CourseView extends BaseView {
    void addItems(List<Course> courses);
}
