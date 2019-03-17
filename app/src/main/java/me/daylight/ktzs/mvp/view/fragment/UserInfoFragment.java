package me.daylight.ktzs.mvp.view.fragment;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.mvp.presenter.UserInfoPresenter;
import me.daylight.ktzs.mvp.view.UserInfoView;
import me.daylight.ktzs.viewmodel.UserInfoViewModel;

/**
 * @author Daylight
 * @date 2019/03/16 19:15
 */
public class UserInfoFragment extends BaseFragment<UserInfoPresenter> implements UserInfoView {
    @BindView(R.id.user_info_groupview)
    QMUIGroupListView infoList;

    @BindView(R.id.user_info_topbar)
    QMUITopBarLayout topBar;

    private UserInfoViewModel userInfoViewModel;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle("个人信息");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        userInfoViewModel= ViewModelProviders.of(getBaseFragmentActivity()).get(UserInfoViewModel.class);
        if (userInfoViewModel.getCourseId()!=null)
            getPresenter().initTeacherInfo(userInfoViewModel.getCourseId());
        else if (userInfoViewModel.getIdNumber()!=null)
            getPresenter().initUserInfo(userInfoViewModel.getIdNumber());
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void setInfo(User user) {
        QMUICommonListItemView name=infoList.createItemView(null,"姓名",user.getName(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView idNumber=infoList.createItemView(null,user.getRole().equals("student")?"学号":"工号",user.getIdNumber(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView phone=infoList.createItemView(null,"手机",user.getPhone(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView role=infoList.createItemView(null,"角色",user.getRole(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(idNumber,null)
                .addItemView(name,null)
                .addItemView(role,null)
                .addItemView(phone,null)
                .addTo(infoList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userInfoViewModel.setCourseId(null);
        userInfoViewModel.setIdNumber(null);
    }
}
