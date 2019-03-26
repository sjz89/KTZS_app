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
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.mvp.presenter.NoticePresenter;
import me.daylight.ktzs.mvp.view.NoticeView;
import me.daylight.ktzs.viewmodel.CourseViewModel;

/**
 * @author Daylight
 * @date 2019/03/12 00:12
 */
public class NoticeFragment extends BaseFragment<NoticePresenter> implements NoticeView {
    @BindView(R.id.notice_list)
    RecyclerView recyclerView;

    @BindView(R.id.notice_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.notice_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.notice_topbar)
    QMUITopBarLayout topBar;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void doAfterView() {
        Course course= ViewModelProviders.of(getBaseFragmentActivity()).get(CourseViewModel.class).getCourse();
        topBar.setTitle(R.string.notice);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(course),1500));
        emptyView.show(true);
        getPresenter().initNotices(course);
    }

    @Override
    protected NoticePresenter createPresenter() {
        return new NoticePresenter();
    }

    @Override
    public void initRecyclerView(List<CommonData> notices) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(notices);
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
}
