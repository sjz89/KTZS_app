package me.daylight.ktzs.entity;

/**
 * @author Daylight
 * @date 2019/03/09 09:23
 */
public class EventMsg {
    private int eventChannel;

    private Object data;

    public EventMsg(int eventChannel, Object data) {
        this.eventChannel = eventChannel;
        this.data = data;
    }

    public int getEventChannel() {
        return eventChannel;
    }

    public void setEventChannel(int eventChannel) {
        this.eventChannel = eventChannel;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
