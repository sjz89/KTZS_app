package me.daylight.ktzs.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

/**
 * @author Daylight
 * @date 2019/03/09 00:41
 */
public class StartSignInDialog extends QMUIDialog.CustomDialogBuilder {

    private Context mContext;

    @BindView(R.id.course_spinner)
    NiceSpinner courseChooser;

    @BindView(R.id.limit_time)
    EditText limitTime;

    private List<Course> courses;

    public StartSignInDialog(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_start_signin,parent);
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

    public Integer getLimitTime(){
        if (limitTime.getText().toString().equals(""))
            return null;
        return Integer.parseInt(limitTime.getText().toString());
    }

    public Long getCourseId(){
        return courses==null?null:courses.get(courseChooser.getSelectedIndex()).getId();
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
}
