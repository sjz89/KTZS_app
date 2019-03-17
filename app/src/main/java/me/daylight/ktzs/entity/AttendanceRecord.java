package me.daylight.ktzs.entity;

/**
 * @author Daylight
 * @date 2019/03/07 23:31
 */
public class AttendanceRecord {
    private Long id;

    private String courseName;

    private int state;

    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
