package me.daylight.ktzs.entity;

import com.qmuiteam.qmui.widget.section.QMUISection;

public class SectionItem implements QMUISection.Model<SectionItem> {
    private Leave leave;

    public SectionItem(Leave leave) {
        this.leave = leave;
    }

    public Leave getLeave() {
        return leave;
    }

    @Override
    public SectionItem cloneForDiff() {
        return new SectionItem(getLeave());
    }

    @Override
    public boolean isSameItem(SectionItem other) {
        return leave.getId().equals(other.getLeave().getId());
    }

    @Override
    public boolean isSameContent(SectionItem other) {
        return leave.getReason().equals(other.leave.getReason());
    }
}
