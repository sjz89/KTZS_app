package me.daylight.ktzs.mvp.view.fragment;

import android.content.Intent;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.activity.LoginActivity;
import me.daylight.ktzs.customview.ChangePwdDialogBuilder;
import me.daylight.ktzs.customview.CommonListItemView;
import me.daylight.ktzs.mvp.presenter.SettingPresenter;
import me.daylight.ktzs.mvp.view.SettingView;

/**
 * @author Daylight
 * @date 2019/03/11 23:22
 */
public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingView {
    @BindView(R.id.setting_grouplist)
    QMUIGroupListView itemView;

    @BindView(R.id.setting_topbar)
    QMUITopBarLayout topBar;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.setting);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        initItemView();
    }

    private void initItemView(){
        QMUICommonListItemView changePwd= new CommonListItemView(getCurContext(),R.drawable.ic_password,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        changePwd.setText("更改密码");

        QMUICommonListItemView logout= new CommonListItemView(getCurContext(),R.drawable.ic_log_out,64,64,
                QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        logout.setText("退出");

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(changePwd,v -> showChangePwdDialog())
                .addItemView(logout,v -> getPresenter().logout())
                .addTo(itemView);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void toLogin() {
        startActivity(new Intent(getBaseFragmentActivity(), LoginActivity.class));
        getBaseFragmentActivity().finish();
    }

    public void showChangePwdDialog() {
        ChangePwdDialogBuilder changePwdBuilder=new ChangePwdDialogBuilder(getCurContext());
        changePwdBuilder
                .setTitle("更改密码")
                .addAction("取消",((dialog, index) -> dialog.dismiss()))
                .addAction("确定",((dialog, index) -> {
                    if (changePwdBuilder.validPassword()) {
                        String oldPwd=changePwdBuilder.getOldPassword();
                        String password=changePwdBuilder.getPassword();
                        getPresenter().changePwd(oldPwd,password);
                        dialog.dismiss();
                    }
                })).show();
    }
}
