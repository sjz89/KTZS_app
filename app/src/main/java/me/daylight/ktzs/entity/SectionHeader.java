package me.daylight.ktzs.entity;

import com.qmuiteam.qmui.widget.section.QMUISection;

public class SectionHeader implements QMUISection.Model<SectionHeader> {
    private int state;

    private String text;

    public SectionHeader(int state) {
        this.state=state;
    }

    public int getState() {
        return state;
    }

    public String getText() {
        switch (state){
            case 0:
                return "审核中";
            case 1:
                return "已批准";
            case 2:
                return "未批准";
            default:
                return null;
        }
    }

    @Override
    public SectionHeader cloneForDiff() {
        return new SectionHeader(getState());
    }

    @Override
    public boolean isSameItem(SectionHeader other) {
        return state==other.state||(text!=null && text.equals(other.text));
    }

    @Override
    public boolean isSameContent(SectionHeader other) {
        return true;
    }
}
