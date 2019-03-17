package me.daylight.ktzs.entity;


import android.view.View;

/**
 * @author Daylight
 * @date 2019/03/10 22:17
 */
public class CommonData {
    private Long id;

    private Image image;

    private String text;

    private String subText;

    private String customText;

    public CommonData(Image image, String text, String subText, Long id) {
        this.image = image;
        this.text = text;
        this.subText = subText;
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }
}
