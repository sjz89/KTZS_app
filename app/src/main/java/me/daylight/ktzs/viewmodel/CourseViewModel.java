package me.daylight.ktzs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import me.daylight.ktzs.entity.Course;

/**
 * @author Daylight
 * @date 2019/03/12 16:29
 */
public class CourseViewModel extends AndroidViewModel {

    private Course course;

    public CourseViewModel(@NonNull Application application) {
        super(application);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
