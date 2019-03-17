package me.daylight.ktzs.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.AttendanceRecord;
import me.daylight.ktzs.mvp.presenter.SignInPresenter;
import me.daylight.ktzs.mvp.view.SignInView;
import me.daylight.ktzs.utils.DialogUtil;

/**
 * @author Daylight
 * @date 2019/03/07 17:15
 */
public class SignInFragment extends BaseFragment<SignInPresenter> implements SignInView {

    private static final int SCANNER_CODE=0xa1;

    @BindView(R.id.signin_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.signin_location)
    TextView locationView;

    @BindView(R.id.signin_scan)
    ImageView scanBtn;

    @BindView(R.id.signin_code)
    EditText signInCode;

    @BindView(R.id.signin_record)
    QMUIGroupListView recordList;

    private QMUICommonListItemView latestRecord;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_signin;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.sign_in);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        getPresenter().getLocation();
        getPresenter().initRecords();

    }

    @Override
    protected SignInPresenter createPresenter() {
        return new SignInPresenter();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showLocation(Location location) {
        if (location!=null)
            locationView.setText("经度:"+location.getLongitude()+",纬度:"+location.getLatitude());
    }

    @Override
    public void openScanner() {
        startActivityForResult(new Intent(getBaseFragmentActivity(), CaptureActivity.class),SCANNER_CODE);
    }

    @Override
    public void initRecord(List<AttendanceRecord> records) {
        latestRecord=recordList.createItemView(null,null,null,QMUICommonListItemView.VERTICAL,QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM,
                QMUIResHelper.getAttrDimen(getCurContext(), R.attr.qmui_list_item_height));
        latestRecord.setVisibility(View.GONE);

        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getCurContext());
        section.setTitle("最近签到")
                .addItemView(latestRecord,null);
        for (AttendanceRecord record:records){
            section.addItemView(addRecordItem(record),null);
        }
        section.addTo(recordList);
    }

    @Override
    public void addLatestRecord(AttendanceRecord record) {
        latestRecord.setVisibility(View.VISIBLE);
        latestRecord.setText(record.getCourseName());
        latestRecord.setDetailText(record.getTime());
        TextView textView=new TextView(getCurContext());
        textView.setWidth(QMUIDisplayHelper.dp2px(getCurContext(),120));
        textView.setGravity(Gravity.END);
        textView.setTextSize(15);
        textView.setTextColor(QMUIResHelper.getAttrColor(getCurContext(),R.attr.qmui_config_color_gray_5));
        textView.setText(record.getState()==1?"已签到":"未签到");
        latestRecord.addAccessoryCustomView(textView);
    }

    @Override
    public void emptyEdit() {
        signInCode.setText("");
    }

    private QMUICommonListItemView addRecordItem(AttendanceRecord record){
        TextView textView=new TextView(getCurContext());
        textView.setWidth(QMUIDisplayHelper.dp2px(getCurContext(),120));
        textView.setGravity(Gravity.END);
        textView.setTextSize(15);
        textView.setTextColor(QMUIResHelper.getAttrColor(getCurContext(),R.attr.qmui_config_color_gray_5));
        textView.setText(record.getState()==1?"已签到":"未签到");
        QMUICommonListItemView itemView=recordList.createItemView(null,record.getCourseName(),record.getTime(),
                QMUICommonListItemView.VERTICAL,QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM, QMUIResHelper.getAttrDimen(getCurContext(), R.attr.qmui_list_item_height));
        itemView.addAccessoryCustomView(textView);

        return itemView;

    }

    @OnClick({R.id.signin_scan,R.id.signin_btn})
    public void onClickListener(View v){
        switch (v.getId()){
            case R.id.signin_scan:
                openScanner();
                break;
            case R.id.signin_btn:
                getPresenter().signIn(signInCode.getText().toString());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==SCANNER_CODE){
            if (data!=null){
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                        String result = bundle.getString(XQRCode.RESULT_DATA);
                        signInCode.setText(result);
                    } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                        DialogUtil.showTipDialog(getCurContext(), QMUITipDialog.Builder.ICON_TYPE_FAIL,"解析二维码失败",true);
                    }
                }
            }
        }
    }
}
