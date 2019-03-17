package me.daylight.ktzs.mvp.view.fragment;

import android.view.Gravity;
import android.widget.EditText;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.daylight.ktzs.R;
import me.daylight.ktzs.mvp.presenter.LeavePresenter;
import me.daylight.ktzs.mvp.view.LeaveView;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public class LeaveFragment extends BaseFragment<LeavePresenter> implements LeaveView {

    @BindView(R.id.leave_groupview)
    QMUIGroupListView itemView;

    @BindView(R.id.leave_topbar)
    QMUITopBarLayout topBar;

    private NiceSpinner niceSpinner;

    private EditText editText;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_leave;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.leave);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        getPresenter().initItemView();
    }

    @Override
    protected LeavePresenter createPresenter() {
        return new LeavePresenter();
    }

    @OnClick(R.id.leave_submit)
    public void onSubmitClick(){
        getPresenter().submitLeaveNote(niceSpinner.getSelectedIndex(),editText.getText().toString());
    }

    @Override
    public void initItemView(List<String> courses) {
        QMUICommonListItemView courseItem=itemView.createItemView("选择课程");
        courseItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);

        niceSpinner=new NiceSpinner(getCurContext());
        niceSpinner.setWidth(QMUIDisplayHelper.dp2px(getCurContext(),150));
        niceSpinner.setGravity(Gravity.END);
        niceSpinner.setTextSize(15);
        niceSpinner.setTextColor(QMUIResHelper.getAttrColor(getCurContext(),R.attr.qmui_config_color_black));
        niceSpinner.attachDataSource(courses);

        courseItem.addAccessoryCustomView(niceSpinner);

        QMUICommonListItemView reasonItem=itemView.createItemView(null,null,null,QMUICommonListItemView.VERTICAL,QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);

        editText=new EditText(getCurContext());
        editText.setWidth(QMUIDisplayHelper.getUsefulScreenWidth(getCurContext()));
        editText.setHeight(QMUIResHelper.getAttrDimen(getCurContext(), R.attr.qmui_list_item_height_higher)-16);
        editText.setGravity(Gravity.START);
        editText.setTextSize(15);
        editText.setTextColor(QMUIResHelper.getAttrColor(getCurContext(),R.attr.qmui_config_color_black));
        editText.setBackgroundColor(getCurContext().getColor(R.color.transparent));
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);

        reasonItem.addAccessoryCustomView(editText);

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(courseItem,null)
                .addTo(itemView);

        QMUIGroupListView.newSection(getCurContext())
                .setTitle("请假理由")
                .addItemView(reasonItem,null)
                .addTo(itemView);
    }
}
