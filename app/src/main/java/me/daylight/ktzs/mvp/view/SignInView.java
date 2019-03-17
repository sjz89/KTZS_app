package me.daylight.ktzs.mvp.view;

import android.location.Location;

import java.util.List;

import me.daylight.ktzs.entity.AttendanceRecord;

/**
 * @author Daylight
 * @date 2019/03/07 17:16
 */
public interface SignInView extends BaseView {
    void showLocation(Location location);

    void openScanner();

    void initRecord(List<AttendanceRecord> records);

    void addLatestRecord(AttendanceRecord record);

    void emptyEdit();
}
