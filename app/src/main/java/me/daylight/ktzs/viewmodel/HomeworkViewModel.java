package me.daylight.ktzs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeworkViewModel extends AndroidViewModel {
    private Long homeworkId;

    public HomeworkViewModel(@NonNull Application application) {
        super(application);
    }

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }
}
