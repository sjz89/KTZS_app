package me.daylight.ktzs.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TeacherRecordViewModel extends AndroidViewModel {
    private String uniqueId;

    public TeacherRecordViewModel(@NonNull Application application) {
        super(application);
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
