package me.daylight.ktzs.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.OnClick;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.User;
import me.daylight.ktzs.mvp.presenter.InfoPresenter;
import me.daylight.ktzs.mvp.view.InfoView;

/**
 * @author Daylight
 * @date 2019/03/06 22:46
 */
public class InfoFragment extends BaseFragment<InfoPresenter> implements InfoView {
    @BindView(R.id.info_groupview)
    QMUIGroupListView infoList;

    @BindView(R.id.info_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.info_edit)
    TextView editBtn;

    private EditText nameEdit;

    private EditText phoneEdit;

    private boolean isEdited;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void doAfterView() {
        isEdited=false;
        topBar.setTitle(R.string.info);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        editable(false);
        getPresenter().loadInfo();
    }

    @Override
    protected InfoPresenter createPresenter() {
        return new InfoPresenter();
    }

    @Override
    public void setInfo(User user) {
        QMUICommonListItemView name=infoList.createItemView("姓名");
        QMUICommonListItemView idNumber=infoList.createItemView(null,user.getRole().equals("student")?"学号":"工号",user.getIdNumber(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView phone=infoList.createItemView("手机");
        QMUICommonListItemView role=infoList.createItemView(null,"角色",user.getRole(),QMUICommonListItemView.HORIZONTAL,QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        nameEdit.setText(user.getName());
        name.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        name.addAccessoryCustomView(nameEdit);
        phoneEdit.setText(user.getPhone());
        phone.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        phone.addAccessoryCustomView(phoneEdit);

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(idNumber,null)
                .addItemView(name,null)
                .addItemView(role,null)
                .addItemView(phone,null)
                .addTo(infoList);
    }

    @Override
    public void editable(boolean b) {
        if (b)
            editBtn.setText(R.string.save);
        else
            editBtn.setText(R.string.edit);
        if (nameEdit==null)
            nameEdit=createEditText(getCurContext());
        if (phoneEdit==null)
            phoneEdit=createEditText(getCurContext());
        nameEdit.setFocusable(b);
        nameEdit.setFocusableInTouchMode(b);
        phoneEdit.setFocusable(b);
        phoneEdit.setFocusableInTouchMode(b);
    }

    @OnClick(R.id.info_edit)
    public void OnEditBtnClick(View v){
        if (((TextView)v).getText().toString().equals(getString(R.string.edit))) {
            editable(true);
        }else if (((TextView)v).getText().toString().equals(getString(R.string.save))) {
            isEdited=true;
            getPresenter().saveInfo(nameEdit.getText().toString(),phoneEdit.getText().toString());
        }
    }

    private EditText createEditText(Context context){
        EditText editText=new EditText(context);
        editText.setWidth(QMUIDisplayHelper.dp2px(context,120));
        editText.setGravity(Gravity.END);
        editText.setTextSize(15);
        editText.setTextColor(QMUIResHelper.getAttrColor(context,R.attr.qmui_config_color_gray_5));
        editText.setBackgroundColor(context.getColor(R.color.transparent));
        return editText;
    }

    @Override
    protected void popBackStack() {
        if (isEdited) {
            Intent intent = new Intent();
            intent.putExtra("name", nameEdit.getText().toString());
            this.setFragmentResult(1, intent);
        }
        super.popBackStack();
    }
}
