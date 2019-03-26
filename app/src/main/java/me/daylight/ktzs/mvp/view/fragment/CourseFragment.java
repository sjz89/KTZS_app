package me.daylight.ktzs.mvp.view.fragment;

import android.view.Gravity;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.mvp.presenter.CoursePresenter;
import me.daylight.ktzs.mvp.view.CourseView;
import me.daylight.ktzs.viewmodel.CourseViewModel;

/**
 * @author Daylight
 * @date 2019/03/08 15:37
 */
public class CourseFragment extends BaseFragment<CoursePresenter> implements CourseView {
    @BindView(R.id.course_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.course_grouplist)
    QMUIGroupListView courseList;

    private CourseViewModel courseViewModel;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void doAfterView() {
        topBar.setTitle(R.string.course);
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        courseViewModel= ViewModelProviders.of(getBaseFragmentActivity()).get(CourseViewModel.class);
        getPresenter().getMyCourses();
    }

    @Override
    protected CoursePresenter createPresenter() {
        return new CoursePresenter();
    }

    @Override
    public void addItems(List<Course> courses) {
        Map<String,QMUIGroupListView.Section> sections=new LinkedHashMap<>();
        for (Course course:courses) {
            switch (course.getTime().split(" ")[0]){
                case "01":
                    createIfAbsent(sections,"周一");
                    Objects.requireNonNull(sections.get("周一")).addItemView(newItem(course), v -> {
                        courseViewModel.setCourse(course);
                        startFragment(new CourseDetailFragment());
                    });
                    break;
                case "02":
                    createIfAbsent(sections,"周二");
                    Objects.requireNonNull(sections.get("周二")).addItemView(newItem(course), v -> {
                        courseViewModel.setCourse(course);
                        startFragment(new CourseDetailFragment());
                    });
                    break;
                case "03":
                    createIfAbsent(sections,"周三");
                    Objects.requireNonNull(sections.get("周三")).addItemView(newItem(course), v -> {
                        courseViewModel.setCourse(course);
                        startFragment(new CourseDetailFragment());
                    });
                    break;
                case "04":
                    createIfAbsent(sections,"周四");
                    Objects.requireNonNull(sections.get("周四")).addItemView(newItem(course), v -> {
                        courseViewModel.setCourse(course);
                        startFragment(new CourseDetailFragment());
                    });
                    break;
                case "05":
                    createIfAbsent(sections,"周五");
                    Objects.requireNonNull(sections.get("周五")).addItemView(newItem(course), v -> {
                        courseViewModel.setCourse(course);
                        startFragment(new CourseDetailFragment());
                    });
                    break;
            }
        }
        for (Map.Entry<String, QMUIGroupListView.Section> entry:sections.entrySet())
            entry.getValue().addTo(courseList);
    }

    private void createIfAbsent(Map<String,QMUIGroupListView.Section> sectionMap,String weekDay){
        if (!sectionMap.containsKey(weekDay)){
            QMUIGroupListView.Section section=QMUIGroupListView.newSection(getCurContext());
            section.setTitle(weekDay);
            sectionMap.put(weekDay,section);
        }
    }

    private QMUICommonListItemView newItem(Course course){
        TextView textView=new TextView(getCurContext());
        textView.setWidth(QMUIDisplayHelper.dp2px(getCurContext(),120));
        textView.setGravity(Gravity.END);
        textView.setTextSize(15);
        textView.setTextColor(QMUIResHelper.getAttrColor(getCurContext(),R.attr.qmui_config_color_gray_5));
        textView.setText(String.format("%s节", course.getTime().split(" ")[1]));
        QMUICommonListItemView itemView=courseList.createItemView(null,course.getName(),course.getTeacherName(),
                QMUICommonListItemView.VERTICAL,QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM,
                QMUIResHelper.getAttrDimen(getCurContext(), R.attr.qmui_list_item_height));
        itemView.getTextView().setTextSize(18);
        itemView.getDetailTextView().setTextSize(15);
        itemView.addAccessoryCustomView(textView);
        return itemView;
    }
}
