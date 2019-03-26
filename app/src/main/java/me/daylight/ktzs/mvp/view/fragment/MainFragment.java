package me.daylight.ktzs.mvp.view.fragment;

import android.content.Intent;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.customview.CommonListItemView;
import me.daylight.ktzs.customview.StartSignInDialog;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.mvp.presenter.MainPresenter;
import me.daylight.ktzs.mvp.view.MainView;

public class MainFragment extends BaseFragment<MainPresenter> implements MainView {

    @BindView(R.id.main_grouplist)
    QMUIGroupListView groupView;

    @BindView(R.id.main_topbar)
    QMUITopBarLayout topBar;

    private QMUICommonListItemView info;

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return QMUIFragment.SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.app_name);
        getPresenter().initInfoView();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    protected void popBackStack() {
        QMUIDialog.MessageDialogBuilder builder=new QMUIDialog.MessageDialogBuilder(getCurContext());
        builder.setMessage("确定要退出吗？")
                .addAction("取消",((dialog, index) -> dialog.dismiss()))
                .addAction("确定",((dialog, index) -> {
                    dialog.dismiss();
                    getPresenter().logout();
                    super.popBackStack();
                }))
                .show();
    }

    @Override
    public void initMenu(User user) {
        info= new CommonListItemView(getCurContext(),user.getRole().equals("student")?R.drawable.ic_student:R.drawable.ic_teacher,128,128,
                QMUICommonListItemView.VERTICAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        info.setText(user.getName());
        info.getTextView().setTextSize(20);
        info.getTextView().setPadding(0,0,0,15);
        info.getDetailTextView().setTextSize(15);
        info.setDetailText(user.getRole().equals("student")?"学号:"+user.getIdNumber():"工号:"+user.getIdNumber());

        QMUICommonListItemView signIn= new CommonListItemView(getCurContext(),R.drawable.ic_icon_signin_line,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        signIn.setText("签到");

        QMUICommonListItemView leave= new CommonListItemView(getCurContext(),R.drawable.ic_leave,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        leave.setText("请假");

        CommonListItemView course= new CommonListItemView(getCurContext(),R.drawable.ic_icon_calendar,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        course.setText("课表");

        CommonListItemView message= new CommonListItemView(getCurContext(),R.drawable.ic_icon_message,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        message.setText("通知");

        CommonListItemView homework= new CommonListItemView(getCurContext(),R.drawable.ic_icon_doc,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        homework.setText("作业");

        CommonListItemView record= new CommonListItemView(getCurContext(),R.drawable.ic_icon_statistics,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        record.setText("记录");

        CommonListItemView setting= new CommonListItemView(getCurContext(),R.drawable.ic_icon_setting,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        setting.setText("设置");

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(info,v -> startFragmentForResult(new InfoFragment(),0x01))
                .addTo(groupView);

        if (user.getRole().equals("student"))
            QMUIGroupListView.newSection(getCurContext())
                .addItemView(signIn,v -> startFragment(new SignInFragment()))
                .addItemView(leave,v -> startFragment(new LeaveFragment()))
                .addItemView(course,v -> startFragment(new CourseFragment()))
                .addItemView(message,v -> startFragment(new NoticeFragment()))
                .addItemView(homework,v -> startFragment(new HomeworkListFragment()))
                .addItemView(record,v -> startFragment(new RecordFragment()))
                .addItemView(setting,v -> startFragment(new SettingFragment()))
                .addTo(groupView);
        else
            QMUIGroupListView.newSection(getCurContext())
                    .addItemView(signIn,v -> getPresenter().getAttendanceNow())
                    .addItemView(course,v -> startFragment(new CourseFragment()))
                    .addItemView(homework,v -> startFragment(new HomeworkListFragment()))
                    .addItemView(record,v -> startFragment(new TeacherRecordFragment()))
                    .addItemView(setting,v -> startFragment(new SettingFragment()))
                    .addTo(groupView);
    }

    @Override
    public void showStartSignInDialog(){
        StartSignInDialog dialog=new StartSignInDialog(getCurContext());
        dialog.setTitle("发起签到")
                .addAction("取消",(((dialog1, index) -> dialog1.dismiss())))
                .addAction("确定",(((dialog1, index) -> {
                    Long courseId=dialog.getCourseId();
                    Integer limitMin=dialog.getLimitTime();
                    if (courseId==null||limitMin==null) {
                        showErrorMsg("请输入相关参数");
                    }else {
                        getPresenter().startSignIn(courseId,limitMin);
                        dialog1.dismiss();
                    }
                })))
                .show();
    }

    @Override
    public void toStartSignInFragment() {
        startFragment(new StartSignInFragment());
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0x01&&resultCode==1)
            info.setText(data.getStringExtra("name"));
    }
}
