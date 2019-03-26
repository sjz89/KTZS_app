package me.daylight.ktzs.mvp.view.fragment;


import android.os.Handler;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.adapter.LeaveNoteAdapter;
import me.daylight.ktzs.customview.LeaveNoteSubmitDialog;
import me.daylight.ktzs.entity.Leave;
import me.daylight.ktzs.entity.SectionHeader;
import me.daylight.ktzs.entity.SectionItem;
import me.daylight.ktzs.mvp.presenter.LeavePresenter;
import me.daylight.ktzs.mvp.view.LeaveView;

/**
 * @author Daylight
 * @date 2019/03/16 21:52
 */
public class LeaveFragment extends BaseFragment<LeavePresenter> implements LeaveView {

    @BindView(R.id.leave_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.leave_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.leave_list)
    QMUIStickySectionLayout sectionLayout;

    @BindView(R.id.leave_refresh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_leave;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.leave);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        topBar.addRightImageButton(R.drawable.ic_icon_compile,R.id.leave).setOnClickListener(v -> {
            LeaveNoteSubmitDialog dialog=new LeaveNoteSubmitDialog(getCurContext());
            dialog.setTitle("请假申请")
                    .addAction("取消",(((dialog1, index) -> dialog1.dismiss())))
                    .addAction("确定",(((dialog1, index) -> {
                        Long startDate=dialog.getStartDate();
                        Long endDate=dialog.getEndDate();
                        String reason=dialog.getReason();
                        if (startDate==null||endDate==null||reason==null) {
                            showErrorMsg("请输入相关参数");
                        }else {
                            getPresenter().submitLeaveNote(startDate,endDate,reason);
                            dialog1.dismiss();
                        }
                    })))
                    .show();
        });
        refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(),1500));
        emptyView.show(true);
        getPresenter().initRecyclerView();
    }

    @Override
    protected LeavePresenter createPresenter() {
        return new LeavePresenter();
    }

    @Override
    public void initRecyclerView(List<Leave> leaves) {
        QMUIStickySectionAdapter<SectionHeader, SectionItem, QMUIStickySectionAdapter.ViewHolder> adapter=new LeaveNoteAdapter();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseFragmentActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        sectionLayout.setLayoutManager(layoutManager);
        sectionLayout.setAdapter(adapter,true);

        List<QMUISection<SectionHeader,SectionItem>> list=new ArrayList<>();
        Map<Integer,List<SectionItem>> sectionMap=new LinkedHashMap<>();
        for (Leave leave:leaves){
            if (!sectionMap.containsKey(leave.getState()))
                sectionMap.put(leave.getState(),new ArrayList<>());
            Objects.requireNonNull(sectionMap.get(leave.getState())).add(new SectionItem(leave));
        }

        for (Map.Entry<Integer,List<SectionItem>> entry:sectionMap.entrySet())
            list.add(new QMUISection<>(new SectionHeader(entry.getKey()), entry.getValue(), false));
        adapter.setData(list);
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
