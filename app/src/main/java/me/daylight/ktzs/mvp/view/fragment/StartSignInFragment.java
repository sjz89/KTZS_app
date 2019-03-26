package me.daylight.ktzs.mvp.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.xuexiang.xqrcode.XQRCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.daylight.ktzs.R;
import me.daylight.ktzs.app.GlideApp;
import me.daylight.ktzs.entity.EventMsg;
import me.daylight.ktzs.http.WsManager;
import me.daylight.ktzs.mvp.presenter.StartSignInPresenter;
import me.daylight.ktzs.mvp.view.StartSignInView;
import me.daylight.ktzs.utils.GlobalField;

/**
 * @author Daylight
 * @date 2019/03/08 16:26
 */
public class StartSignInFragment extends BaseFragment<StartSignInPresenter> implements StartSignInView {
    @BindView(R.id.start_signin_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.start_signin_qrcode)
    ImageView qrCodeView;

    @BindView(R.id.start_signin_group)
    QMUIGroupListView groupView;

    @BindView(R.id.start_signin_hint)
    TextView hintView;

    @BindView(R.id.start_signin_stop)
    TextView stopBtn;

    private QMUICommonListItemView uniqueIdItem;

    private QMUICommonListItemView remainTimeItem;

    private QMUICommonListItemView countItem;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_start_signin;
    }

    @Override
    protected void doAfterView() {
        WsManager.getInstance().init(WsManager.Channel_SignInCount);
        EventBus.getDefault().register(this);
        topBar.setTitle(R.string.sign_in);
        topBar.addLeftBackImageButton().setOnClickListener(v->popBackStack());
        getPresenter().initState();
    }

    @Override
    protected StartSignInPresenter createPresenter() {
        return new StartSignInPresenter();
    }

    @Override
    public void initViews(String uniqueId){
        hintView.setText(String.format("在浏览器中访问\n%ssignIn/%s\n可以展示该二维码", GlobalField.url, uniqueId));

        uniqueIdItem=groupView.createItemView("签到码");
        remainTimeItem=groupView.createItemView("剩余时间");
        countItem=groupView.createItemView("已签到人数");
        uniqueIdItem.getDetailTextView().setTextSize(16);
        uniqueIdItem.setDetailText(uniqueId);
        remainTimeItem.getDetailTextView().setTextSize(16);
        countItem.getDetailTextView().setTextSize(16);

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(uniqueIdItem,null)
                .addItemView(remainTimeItem,null)
                .addItemView(countItem,v -> startFragment(new SignInDetailFragment()))
                .addTo(groupView);
    }

    @Override
    public void createQRCode(String uniqueId) {
        GlideApp.with(getCurContext())
                .load(XQRCode.createQRCodeWithLogo(uniqueId,1200,1200,null))
                .override(640,640)
                .into(qrCodeView);
    }

    @Override
    public void showRemainTime(String time) {
        remainTimeItem.setDetailText(time);
    }

    @Override
    public void showCount(int count) {
        countItem.setDetailText(String.valueOf(count)+"人");
    }

    @Override
    public void changeBtn() {
        stopBtn.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCount(EventMsg msg){
        if (msg.getEventChannel()==GlobalField.Event_Channel_SignInCount)
            showCount((int)msg.getData());
    }

    @OnClick(R.id.start_signin_stop)
    public void onStopBtnClick(){
        getPresenter().stopSignIn(uniqueIdItem.getDetailText().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().stopCountDown();
        WsManager.getInstance().disconnect(WsManager.Channel_SignInCount);
        EventBus.getDefault().unregister(this);
    }
}
