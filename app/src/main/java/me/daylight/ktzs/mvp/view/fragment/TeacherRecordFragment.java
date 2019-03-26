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
import me.daylight.ktzs.mvp.presenter.TeacherRecordPresenter;
import me.daylight.ktzs.mvp.view.TeacherRecordView;
import me.daylight.ktzs.viewmodel.TeacherRecordViewModel;

public class TeacherRecordFragment extends BaseFragment<TeacherRecordPresenter> implements TeacherRecordView {
    @BindView(R.id.teacher_record_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.teacher_record_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.teacher_record_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.teacher_record_list)
    RecyclerView recyclerView;

    private TeacherRecordViewModel teacherRecordViewModel;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_teacher_record;
    }

    @Override
    protected void doAfterView() {
        teacherRecordViewModel= ViewModelProviders.of(getBaseFragmentActivity()).get(TeacherRecordViewModel.class);
        topBar.setTitle(R.string.record);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(),1500));
        emptyView.show(true);
        getPresenter().initRecyclerView();
    }

    @Override
    protected TeacherRecordPresenter createPresenter() {
        return new TeacherRecordPresenter();
    }

    @Override
    public void hideRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void initRecyclerView(List<CommonData> recordList) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(recordList);
        adapter.setOnCommonItemClickListener((v,position)->{
            teacherRecordViewModel.setUniqueId(recordList.get(position).getSubText());
            startFragment(new RecordFragment());
        });
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
