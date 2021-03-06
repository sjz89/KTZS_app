package me.daylight.ktzs.mvp.view.fragment;

import android.os.Handler;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.adapter.CommonAdapter;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.mvp.presenter.RecordPresenter;
import me.daylight.ktzs.mvp.view.RecordView;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import me.daylight.ktzs.viewmodel.TeacherRecordViewModel;

/**
 * @author Daylight
 * @date 2019/03/10 01:12
 */
public class RecordFragment extends BaseFragment<RecordPresenter> implements RecordView {

    @BindView(R.id.record_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.record_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.record_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.record_list)
    RecyclerView recyclerView;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.record);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        String uniqueId=null;
        if (SharedPreferencesUtil.getString(getCurContext(), GlobalField.USER,"role").equals("teacher")){
            TeacherRecordViewModel viewModel= ViewModelProviders.of(getBaseFragmentActivity()).get(TeacherRecordViewModel.class);
            uniqueId=viewModel.getUniqueId();
        }
        String finalUniqueId = uniqueId;
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(finalUniqueId),1500));
        emptyView.show(true);
        getPresenter().initRecyclerView(uniqueId);
    }

    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    public void hideRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void initRecyclerView(List<CommonData> recordList) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(recordList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseFragmentActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getCurContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void hideLoading() {
        new Handler().postDelayed(()->emptyView.hide(),1000);
    }

    @Override
    public void showEmptyInfo(String info) {
        emptyView.show(info,null);
    }
}
