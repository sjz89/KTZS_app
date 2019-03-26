package me.daylight.ktzs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.section.QMUIDefaultStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.daylight.ktzs.R;
import me.daylight.ktzs.customview.SectionLoadingItemView;
import me.daylight.ktzs.entity.SectionHeader;
import me.daylight.ktzs.entity.SectionItem;

public class LeaveNoteAdapter extends QMUIDefaultStickySectionAdapter<SectionHeader, SectionItem> {

    @NonNull
    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_leave_header,viewGroup,false);
        return new LeaveHeaderViewHolder(view);
    }

    @NonNull
    @Override
    protected LeaveItemViewHolder onCreateSectionItemViewHolder(@NonNull ViewGroup viewGroup) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_leave_note,viewGroup,false);
        return new LeaveItemViewHolder(view,viewGroup.getContext());
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionLoadingViewHolder(@NonNull ViewGroup viewGroup) {
        return new ViewHolder(new SectionLoadingItemView(viewGroup.getContext()));
    }

    @Override
    protected void onBindSectionHeader(final ViewHolder holder, final int position, QMUISection<SectionHeader, SectionItem> section) {
        ((LeaveHeaderViewHolder) holder).header.setText(section.getHeader().getText());
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<SectionHeader, SectionItem> section, int itemIndex) {
        if (holder instanceof LeaveItemViewHolder){
            ((LeaveItemViewHolder)holder).dateRange.setDetailText(String.format("%s至%s", section.getItemAt(itemIndex).getLeave().getStartDate(), section.getItemAt(itemIndex).getLeave().getEndDate()));
            ((LeaveItemViewHolder)holder).reason.setText(section.getItemAt(itemIndex).getLeave().getReason());
            ((LeaveItemViewHolder)holder).time.setDetailText(section.getItemAt(itemIndex).getLeave().getTime());
            if (section.getItemAt(itemIndex).getLeave().getRemark()!=null)
                ((LeaveItemViewHolder)holder).remark.setText(section.getItemAt(itemIndex).getLeave().getRemark());
            else
                ((LeaveItemViewHolder)holder).remarkLayout.setVisibility(View.GONE);
        }
    }

    static class LeaveHeaderViewHolder extends ViewHolder{

        @BindView(R.id.leave_header)
        TextView header;

        LeaveHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class LeaveItemViewHolder extends ViewHolder {

        @BindView(R.id.leave_note_date_range)
        QMUICommonListItemView dateRange;

        @BindView(R.id.leave_note_time)
        QMUICommonListItemView time;

        @BindView(R.id.leave_note_reason)
        TextView reason;

        @BindView(R.id.leave_note_remark)
        TextView remark;

        @BindView(R.id.leave_note_remark_layout)
        LinearLayout remarkLayout;

        LeaveItemViewHolder(View itemView,Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            dateRange.setText("请假时间");
            dateRange.getDetailTextView().setTextColor(context.getColor(R.color.qmui_config_color_gray_3));
            dateRange.setOrientation(QMUICommonListItemView.HORIZONTAL);
            time.setText("申请时间");
            time.setOrientation(QMUICommonListItemView.HORIZONTAL);
        }
    }
}
