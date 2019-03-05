package me.daylight.ktzs.utils;


import androidx.recyclerview.widget.RecyclerView;

public class SlideScrollListener extends RecyclerView.OnScrollListener {
    private HideScrollListener listener;
    private static final int THRESHOLD = 20;
    private int distance = 0;
    private boolean visible = true;//是否可见

    protected SlideScrollListener(HideScrollListener listener) {
        this.listener=listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (distance > THRESHOLD && visible) {
            //隐藏动画
            visible = false;
            listener.onHide();
            distance = 0;
        } else if (distance < -20 && !visible) {
            //显示动画
            visible = true;
            listener.onShow();
            distance = 0;
        }

        if (visible && dy > 0 || (!visible && dy < 0)) {
            distance += dy;
        }
    }

    public interface HideScrollListener {
        void onHide();

        void onShow();
    }
}
