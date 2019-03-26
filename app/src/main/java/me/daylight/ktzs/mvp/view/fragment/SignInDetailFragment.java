package me.daylight.ktzs.mvp.view.fragment;

import android.os.Handler;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.adapter.CommonAdapter;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.EventMsg;
import me.daylight.ktzs.mvp.presenter.SignInDetailPresenter;
import me.daylight.ktzs.mvp.view.SignInDetailView;
import me.daylight.ktzs.utils.GlobalField;

public class SignInDetailFragment extends BaseFragment<SignInDetailPresenter> implements SignInDetailView {
    @BindView(R.id.sign_in_detail_list)
    RecyclerView recyclerView;

    @BindView(R.id.sign_in_detail_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.sign_in_detail_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.sign_in_detail_topbar)
    QMUITopBarLayout topBar;

    private CommonAdapter adapter;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_sign_in_detail;
    }

    @Override
    protected void doAfterView() {
        EventBus.getDefault().register(this);
        topBar.setTitle("已签到列表");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(),1500));
        emptyView.show(true);
        getPresenter().initStudentsList();
    }

    @Override
    protected SignInDetailPresenter createPresenter() {
        return new SignInDetailPresenter();
    }

    @Override
    public void initRecyclerView(List<CommonData> students) {
        adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(students);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseFragmentActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getCurContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void hideRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void hideLoading() {
        new Handler().postDelayed(()->emptyView.hide(),1000);
    }

    @Override
    public void showEmptyInfo(String info) {
        emptyView.show(info,null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(EventMsg msg) throws JSONException {
        if (msg.getEventChannel()== GlobalField.Event_Channel_SignInDetail){
            JSONObject jsonObject=new JSONObject((String)msg.getData());
            CommonData commonData=new CommonData(null,jsonObject.getString("name"),
                    jsonObject.getString("idNumber"),jsonObject.getLong("id"));
            commonData.setCustomText(jsonObject.getString("time"));
            adapter.addData(commonData);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
