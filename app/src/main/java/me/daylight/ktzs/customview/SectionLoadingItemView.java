package me.daylight.ktzs.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SectionLoadingItemView extends FrameLayout {

    private QMUILoadingView mLoadingView;

    public SectionLoadingItemView(@NonNull Context context) {
        this(context, null);
    }

    public SectionLoadingItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLoadingView = new QMUILoadingView(context,
                QMUIDisplayHelper.dp2px(context, 24), Color.LTGRAY);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(mLoadingView, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                QMUIDisplayHelper.dp2px(getContext(), 48), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mLoadingView.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLoadingView.stop();
    }
}
