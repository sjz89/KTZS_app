package me.daylight.ktzs.mvp.view.fragment;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Map;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.mvp.presenter.HomeworkDetailPresenter;
import me.daylight.ktzs.mvp.view.HomeworkDetailView;
import me.daylight.ktzs.viewmodel.HomeworkViewModel;

public class HomeworkDetailFragment extends BaseFragment<HomeworkDetailPresenter> implements HomeworkDetailView {

    @BindView(R.id.homework_detail_grouplist)
    QMUIGroupListView itemView;

    @BindView(R.id.homework_detail_topbar)
    QMUITopBarLayout topBar;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_homework_detail;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle("作业详情");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        Long homeworkId=ViewModelProviders.of(getBaseFragmentActivity()).get(HomeworkViewModel.class).getHomeworkId();
        getPresenter().initItemView(homeworkId);
    }

    @Override
    protected HomeworkDetailPresenter createPresenter() {
        return new HomeworkDetailPresenter();
    }

    @Override
    public void initItemView(Map<String,Object> homework) {
        QMUICommonListItemView title=itemView.createItemView("标题");
        title.setDetailText((String)homework.get("name"));

        QMUICommonListItemView course=itemView.createItemView("所属课程");
        course.setDetailText((String)homework.get("courseName"));

        QMUICommonListItemView content=itemView.createItemView("内容");
        content.setDetailText((String)homework.get("content"));

        QMUICommonListItemView entTime=itemView.createItemView("截止时间");
        entTime.setDetailText((String)homework.get("endTime"));

        QMUICommonListItemView createTime=itemView.createItemView("发布时间");
        createTime.setDetailText((String)homework.get("createTime"));

        QMUICommonListItemView count=itemView.createItemView("已提交人数");
        count.setDetailText((String)homework.get("count"));

        QMUIGroupListView.newSection(getCurContext())
                .addItemView(title,null)
                .addItemView(course,null)
                .addItemView(content,null)
                .addItemView(entTime,null)
                .addItemView(createTime,null)
                .addItemView(count,v -> startFragment(new HomeworkFileFragment()))
                .addTo(itemView);

    }
}
