package me.daylight.ktzs.entity;

import android.graphics.drawable.Drawable;

/**
 * @author Daylight
 * @date 2019/03/11 21:07
 */
public class Image {
    private Drawable drawable;

    private int width;

    private int height;

    public Image(Drawable drawable, int width, int height) {
        this.drawable = drawable;
        this.width = width;
        this.height = height;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
