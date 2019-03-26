package me.daylight.ktzs.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.http.HttpContract;
import me.daylight.ktzs.http.HttpObserver;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.http.RetrofitUtils;
import me.daylight.ktzs.utils.DialogUtil;
import me.daylight.ktzs.utils.GlobalField;

public class PublishHomeworkDialog extends QMUIDialog.CustomDialogBuilder {

    private Context mContext;

    @BindView(R.id.course_spinner)
    NiceSpinner courseChooser;

    @BindView(R.id.homework_title)
    EditText title;

    @BindView(R.id.end_time)
    TextView endTimeView;

    @BindView(R.id.homework_content)
    EditText content;

    private DatePickerDialog dialog;

    private Long endTime;

    private List<Course> courses;

    public PublishHomeworkDialog(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_publish_homework,parent);
        ButterKnife.bind(this,view);

        initCourses(new OnHttpCallBack<RetResult<List<Course>>>() {
            @Override
            public void onSuccess(RetResult<List<Course>> courseRetResult) {
                courses=courseRetResult.getData();
                List<String> courseList=new ArrayList<>();
                for (Course course:courses)
                    courseList.add(course.getName()+"("+course.getTime()+")");
                courseChooser.attachDataSource(courseList);
            }

            @Override
            public void onFailed(String errorMsg) {
                showErrorMsg(errorMsg);
            }
        });
    }

    public Long getCourseId(){
        return courses==null?null:courses.get(courseChooser.getSelectedIndex()).getId();
    }

    public String getHomeworkTitle(){
        return title.getText().toString();
    }

    public Long getEndTime(){
        return endTime;
    }

    public String getContent(){
        return content.getText().toString();
    }

    private void initCourses(OnHttpCallBack<RetResult<List<Course>>> callBack){
        RetrofitUtils.newInstance(GlobalField.url)
                .create(HttpContract.class)
                .getMyCourses(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RetResult<List<Course>>>(callBack) {});
    }

    private void showErrorMsg(String errorMsg){
        DialogUtil.showTipDialog(mContext, QMUITipDialog.Builder.ICON_TYPE_FAIL,errorMsg,true);
    }

    @OnClick(R.id.end_time)
    public void onItemClick(){
        Calendar now=Calendar.getInstance();
        if (dialog==null)
            dialog= DatePickerDialog.newInstance(onEndDateSet,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
        dialog.setMinDate(Calendar.getInstance());
        dialog.setOkText("确定");
        dialog.setCancelText("取消");
        dialog.setTitle("选择日期");
        dialog.show(((QMUIFragmentActivity)mContext).getSupportFragmentManager(),"DatePickerDialog");
    }

    private DatePickerDialog.OnDateSetListener onEndDateSet= (view, year, monthOfYear, dayOfMonth) -> {
        endTimeView.setText(String.format(Locale.CHINA,"%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth,23,59,59);
        endTime=calendar.getTimeInMillis();
    };
}
