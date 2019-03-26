package me.daylight.ktzs.mvp.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import me.daylight.fileselector.activity.MediaStoreActivity;
import me.daylight.ktzs.R;
import me.daylight.ktzs.adapter.CommonAdapter;
import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.mvp.presenter.HomeworkFilePresenter;
import me.daylight.ktzs.mvp.view.HomeworkFileView;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.RandomUtil;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import me.daylight.ktzs.viewmodel.HomeworkViewModel;

public class HomeworkFileFragment extends BaseFragment<HomeworkFilePresenter> implements HomeworkFileView {
    @BindView(R.id.homework_file_list)
    RecyclerView recyclerView;

    @BindView(R.id.homework_file_empty)
    QMUIEmptyView emptyView;

    @BindView(R.id.homework_file_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.homework_file_topbar)
    QMUITopBarLayout topBar;

    private Long homeworkId;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_homework_files;
    }

    @Override
    protected void doAfterView() {
        homeworkId=ViewModelProviders.of(getBaseFragmentActivity()).get(HomeworkViewModel.class).getHomeworkId();
        topBar.setTitle("已提交列表");
        topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        if (SharedPreferencesUtil.getString(getCurContext(), GlobalField.USER,"role").equals("student"))
            topBar.addRightImageButton(R.drawable.ic_upload,R.id.upload).setOnClickListener(v -> new QMUIDialog.MenuDialogBuilder(getCurContext())
                    .setTitle("提交作业")
                    .addItem("从手机上传",((dialog, which) -> {
                        dialog.dismiss();
                        startActivityForResult(new Intent(getCurContext(),MediaStoreActivity.class),GlobalField.REQUEST_CODE_CHOOSE);
                    }))
                    .addItem("从电脑上传",((dialog, which) -> {
                        dialog.dismiss();
                        String randomCode=RandomUtil.getRandomString(4);
                        getPresenter().setRandomCode(randomCode,homeworkId);
                        new QMUIDialog.MessageDialogBuilder(getCurContext())
                                .setTitle("电脑上传文件")
                                .setMessage("上传码："+ randomCode +"\n上传地址："+GlobalField.url+"upload")
                                .addAction("确定",((dialog1, index) -> dialog1.dismiss()))
                                .show();
                    }))
                    .show());
        else
            topBar.addRightImageButton(R.drawable.ic_download, R.id.download).setOnClickListener(v -> {
                String randomCode=RandomUtil.getRandomString(4);
                getPresenter().setRandomCode(randomCode,homeworkId);
                new QMUIDialog.MessageDialogBuilder(getCurContext())
                        .setTitle("打包下载")
                        .setMessage("提取码："+ randomCode +"\n下载地址：" + GlobalField.url + "download")
                        .addAction("确定", ((dialog1, index) -> dialog1.dismiss()))
                        .show();
            });
        refreshLayout.setColorSchemeResources(R.color.aqua, R.color.grass,R.color.grapefruit);
        refreshLayout.setOnRefreshListener(()-> new Handler().postDelayed(()->getPresenter().swipeToRefresh(homeworkId),1500));
        emptyView.show(true);
        getPresenter().initFileList(homeworkId);
    }

    @Override
    protected HomeworkFilePresenter createPresenter() {
        return new HomeworkFilePresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalField.REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            getPresenter().uploadHeadImage(data.getStringExtra("filepath"),homeworkId);
        }
    }

    @Override
    public void initRecyclerView(List<CommonData> files) {
        CommonAdapter adapter = new CommonAdapter(getCurContext(), QMUICommonListItemView.VERTICAL, QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        adapter.setData(files);

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
