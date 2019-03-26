package me.daylight.ktzs.customview;

import android.content.Context;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DateRangeLimiter;


import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.daylight.ktzs.R;

public class LeaveNoteSubmitDialog extends QMUIDialog.CustomDialogBuilder{

    @BindView(R.id.reason)
    EditText reason;

    @BindView(R.id.start_date)
    QMUICommonListItemView startDateItem;

    @BindView(R.id.end_date)
    QMUICommonListItemView endDateItem;

    private Context mContext;

    private DatePickerDialog dialog;

    private Long startDate;

    private Long endDate;

    public LeaveNoteSubmitDialog(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_submit_leave,parent);
        ButterKnife.bind(this,view);
        initItem();
    }

    private void initItem(){
        startDate=new Date().getTime();
        endDate=new Date().getTime();
        startDateItem.setText("开始日期");
        endDateItem.setText("结束日期");
        startDateItem.setOrientation(QMUICommonListItemView.HORIZONTAL);
        endDateItem.setOrientation(QMUICommonListItemView.HORIZONTAL);
        startDateItem.setOnClickListener(v -> onItemClick(0));
        endDateItem.setOnClickListener(v -> onItemClick(1));
    }

    private void onItemClick(int witch){
        Calendar now=Calendar.getInstance();
        if (dialog==null)
            dialog=DatePickerDialog.newInstance(witch==0?onStartDateSet:onEndDateSet,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
        dialog.setDateRangeLimiter(witch==0?startLimiter:endLimiter);
        dialog.setOkText("确定");
        dialog.setCancelText("取消");
        dialog.setTitle("选择日期");
        dialog.show(((QMUIFragmentActivity)mContext).getSupportFragmentManager(),"DatePickerDialog");
    }

    public String getReason(){
        return reason.getText().toString();
    }

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    private DatePickerDialog.OnDateSetListener onEndDateSet= (view, year, monthOfYear, dayOfMonth) -> {
        endDateItem.setDetailText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth,23,59);
        endDate=calendar.getTimeInMillis();
    };

    private DatePickerDialog.OnDateSetListener onStartDateSet= (view, year, monthOfYear, dayOfMonth) -> {
        startDateItem.setDetailText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        dialog.initialize(onEndDateSet,year,monthOfYear,dayOfMonth);
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth,0,0);
        startDate=calendar.getTimeInMillis();
    };

    private DateRangeLimiter startLimiter =new DateRangeLimiter() {
        @NonNull
        @Override
        public Calendar getStartDate() {
            return Calendar.getInstance();
        }

        @NonNull
        @Override
        public Calendar getEndDate() {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,6);
            return calendar;
        }

        @Override
        public boolean isOutOfRange(int year, int month, int day) {
            return false;
        }

        @NonNull
        @Override
        public Calendar setToNearestDate(@NonNull Calendar day) {
            return day;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };

    private DateRangeLimiter endLimiter =new DateRangeLimiter() {
        @NonNull
        @Override
        public Calendar getStartDate() {
            Date date=new Date(startDate);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }

        @NonNull
        @Override
        public Calendar getEndDate() {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,6);
            return calendar;
        }

        @Override
        public boolean isOutOfRange(int year, int month, int day) {
            return false;
        }

        @NonNull
        @Override
        public Calendar setToNearestDate(@NonNull Calendar day) {
            return day;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    };
}
