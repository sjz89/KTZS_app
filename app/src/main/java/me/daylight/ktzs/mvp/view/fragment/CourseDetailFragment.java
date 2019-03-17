package me.daylight.ktzs.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import me.daylight.ktzs.R;
import me.daylight.ktzs.adapter.CommonAdapter;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.mvp.presenter.CourseDetailPresenter;
import me.daylight.ktzs.mvp.view.CourseDetailView;
import me.daylight.ktzs.utils.DateUtil;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import me.daylight.ktzs.viewmodel.CourseViewModel;
import me.daylight.ktzs.viewmodel.UserInfoViewModel;

/**
 * @author Daylight
 * @date 2019/03/12 17:31
 */
public class CourseDetailFragment extends BaseFragment<CourseDetailPresenter> implements CourseDetailView {

    @BindView(R.id.viewpager_topbar)
    QMUITopBarLayout topBar;

    @BindView(R.id.viewpager_tab)
    QMUITabSegment tabSegment;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private CourseViewModel courseViewModel;

    private UserInfoViewModel userInfoViewModel;

    private QMUIGroupListView itemView;

    private SwipeRefreshLayout refreshLayout;

    private QMUIEmptyView emptyView;

    private RecyclerView recyclerView;

    private Map<Pager, View> mPageMap = new HashMap<>();

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void doAfterView() {
        courseViewModel = ViewModelProviders.of(getBaseFragmentActivity()).get(CourseViewModel.class);
        userInfoViewModel=ViewModelProviders.of(getBaseFragmentActivity()).get(UserInfoViewModel.class);
        topBar.setTitle("课程详情");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        if (SharedPreferencesUtil.getString(getCurContext(), GlobalField.USER, "role").equals("teacher"))
            topBar.addRightImageButton(R.drawable.ic_icon_send, R.id.send).setOnClickListener(v -> {
                QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getCurContext());
                builder.setTitle("发布通知")
                        .addAction("取消", ((dialog, index) -> dialog.dismiss()))
                        .addAction("确定", ((dialog, index) -> {
                            Long courseId = courseViewModel.getCourse().getId();
                            String content = builder.getEditText().getText().toString();
                            Map<String, Object> noticeMap = new HashMap<>();
                            Map<String, Long> courseMap = new HashMap<>();
                            courseMap.put("id", courseId);
                            noticeMap.put("content", content);
                            noticeMap.put("course", courseMap);
                            getPresenter().push(noticeMap);
                            dialog.dismiss();
                        }))
                        .show();
            });
        initTabs();
        initPagers();
    }

    @Override
    protected CourseDetailPresenter createPresenter() {
        return new CourseDetailPresenter();
    }

    private void initTabs() {
        QMUITabSegment.Tab detail = new QMUITabSegment.Tab(getResources().getString(R.string.title_detail));

        QMUITabSegment.Tab members = new QMUITabSegment.Tab(getResources().getString(R.string.title_members));

        tabSegment.addTab(detail)
                .addTab(members);
    }

    @SuppressLint("InflateParams")
    private View getPageView(Pager pager){
        View view=mPageMap.get(pager);
        if (view==null){
            if (pager==Pager.DETAIL) {
                view = LayoutInflater.from(getCurContext()).inflate(R.layout.fragment_course_detail, null);
                itemView=view.findViewById(R.id.course_detail_grouplist);
                getPresenter().initDetailInfo(courseViewModel.getCourse().getId());
            }
            else {
                view = LayoutInflater.from(getCurContext()).inflate(R.layout.fragment_course_members, null);
                recyclerView=view.findViewById(R.id.course_members_list);
                refreshLayout=view.findViewById(R.id.course_members_refresh);
                emptyView=view.findViewById(R.id.course_members_empty);
                Long courseId=courseViewModel.getCourse().getId();
                refreshLayout.setColorSchemeResources(R.color.aqua,R.color.grass,R.color.grapefruit);
                refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(courseId),1500));
                emptyView.show(true);
                getPresenter().initMembers(courseId);
            }
            mPageMap.put(pager,view);
        }
        return view;
    }

    @Override
    public void initItemView() {
        Course course=courseViewModel.getCourse();
        QMUICommonListItemView courseName=itemView.createItemView("课程名称");
        courseName.setDetailText(course.getName());

        QMUICommonListItemView teacherName=itemView.createItemView("任课教师");
        teacherName.setDetailText(course.getTeacherName());

        QMUICommonListItemView time=itemView.createItemView("上课时间");
        String classTime="";
        switch (course.getTime().split(" ")[0]){
            case "01":classTime="周一 "+course.getTime().split(" ")[1]+"节";break;
            case "02":classTime="周二 "+course.getTime().split(" ")[1]+"节";break;
            case "03":classTime="周三 "+course.getTime().split(" ")[1]+"节";break;
            case "04":classTime="周四 "+course.getTime().split(" ")[1]+"节";break;
            case "05":classTime="周五 "+course.getTime().split(" ")[1]+"节";break;
        }
        time.setDetailText(classTime.replace("0",""));

        QMUIGroupListView.newSection(getCurContext())
                .setTitle("课程信息")
                .addItemView(courseName,null)
                .addItemView(teacherName,v -> {
                    userInfoViewModel.setCourseId(course.getId());
                    startFragment(new UserInfoFragment());
                })
                .addItemView(time,null)
                .addTo(itemView);
    }

    @Override
    public void initLatestRecord(Map<String, String> map) {
        QMUICommonListItemView time=itemView.createItemView("签到时间");
        time.setDetailText(map.get("time"));

        QMUICommonListItemView count=itemView.createItemView("签到人数");
        count.setDetailText(map.get("count")+"人");

        QMUIGroupListView.newSection(getCurContext())
                .setTitle("最近签到")
                .addItemView(time,null)
                .addItemView(count,null)
                .addTo(itemView);
    }

    @Override
    public void initLatestNotice(Notice notice) {
        QMUICommonListItemView time=itemView.createItemView("通知时间");
        time.setDetailText(DateUtil.dateToStr("yyyy-MM-dd HH:mm:ss",new Date(notice.getTime())));

        QMUICommonListItemView content=itemView.createItemView("通知内容");
        content.setDetailText(notice.getContent());

        QMUIGroupListView.newSection(getCurContext())
                .setTitle("最新通知")
                .addItemView(time,null)
                .addItemView(content,null)
                .addTo(itemView);
    }

    @Override
    public void initRecyclerView(List<CommonData> userList) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        adapter.setData(userList);
        adapter.setOnCommonItemClickListener((view, position) -> {
            userInfoViewModel.setIdNumber(userList.get(position).getSubText());
            startFragment(new UserInfoFragment());
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseFragmentActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getCurContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void hideLoading() {
        emptyView.hide();
    }

    @Override
    public void showEmptyInfo(String info) {
        emptyView.show(info,null);
    }

    @Override
    public void hideRefresh() {
        refreshLayout.setRefreshing(false);
    }

    private void initPagers() {
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public int getCount() {
                return Pager.SIZE;
            }

            @NonNull
            @Override
            public Object instantiateItem(final ViewGroup container, int position) {
                Pager page = Pager.getPagerFromPosition(position);
                View view = getPageView(page);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                container.addView(view, params);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

        };

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(0,false);
        tabSegment.setupWithViewPager(viewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
    }

    enum Pager {
        DETAIL, MEMBERS;
        public static final int SIZE=2;
        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return DETAIL;
                case 1:
                    return MEMBERS;
                default:
                    return DETAIL;
            }
        }
    }
}
