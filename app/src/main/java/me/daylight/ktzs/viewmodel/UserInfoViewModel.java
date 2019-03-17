package me.daylight.ktzs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author Daylight
 * @date 2019/03/16 21:04
 */
public class UserInfoViewModel extends AndroidViewModel {
    private String idNumber;

    private Long courseId;

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
