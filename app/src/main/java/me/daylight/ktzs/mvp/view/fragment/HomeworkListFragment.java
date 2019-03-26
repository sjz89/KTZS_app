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
import me.daylight.ktzs.customview.PublishHomeworkDialog;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.mvp.presenter.HomeworkListPresenter;
import me.daylight.ktzs.mvp.view.HomeworkListView;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import me.daylight.ktzs.viewmodel.HomeworkViewModel;

public class HomeworkListFragment extends BaseFragment<HomeworkListPresenter> implements HomeworkListView {
    @BindView(R.id.homework_list)
    RecyclerView recyclerView;

    @BindView(R.id.homework_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.homework_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.homework_topbar)
    QMUITopBarLayout topBar;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_homework;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle("作业");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        if (SharedPreferencesUtil.getString(getCurContext(), GlobalField.USER,"role").equals("teacher"))
            topBar.addRightImageButton(R.drawable.ic_icon_send,R.id.publish).setOnClickListener(v -> {
                PublishHomeworkDialog homeworkDialog=new PublishHomeworkDialog(getCurContext());
                homeworkDialog.setTitle("发布作业")
                        .addAction("取消",(dialog, index) -> dialog.dismiss())
                        .addAction("发布",((dialog, index) -> {
                            Long endTime=homeworkDialog.getEndTime();
                            String title=homeworkDialog.getHomeworkTitle();
                            Long courseId=homeworkDialog.getCourseId();
                            String content=homeworkDialog.getContent();
                            getPresenter().publishHomework(courseId,title,content,endTime);
                            dialog.dismiss();
                        }))
                        .show();
            });
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(),1500));
        emptyView.show(true);
        getPresenter().initHomeworkList();
    }

    @Override
    protected HomeworkListPresenter createPresenter() {
        return new HomeworkListPresenter();
    }

    @Override
    public void initRecyclerView(List<CommonData> homeworks) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(homeworks);
        adapter.setOnCommonItemClickListener((view,position)->{
            ViewModelProviders.of(getBaseFragmentActivity()).get(HomeworkViewModel.class).setHomeworkId(homeworks.get(position).getId());
            startFragment(new HomeworkDetailFragment());
        });
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
