package me.daylight.ktzs.mvp.presenter;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import me.daylight.ktzs.entity.CommonData;
import me.daylight.ktzs.entity.UploadFile;
import me.daylight.ktzs.http.OnHttpCallBack;
import me.daylight.ktzs.http.RetResult;
import me.daylight.ktzs.mvp.model.HomeworkFileModel;
import me.daylight.ktzs.mvp.view.HomeworkFileView;
import me.daylight.ktzs.utils.DialogUtil;

public class HomeworkFilePresenter extends BasePresenter<HomeworkFileView, HomeworkFileModel> {
    public void initFileList(Long homeworkId){
        getModel().getFileList(homeworkId, new OnHttpCallBack<RetResult<List<UploadFile>>>() {
            @Override
            public void onSuccess(RetResult<List<UploadFile>> listRetResult) {
                getView().initRecyclerView(transformData(listRetResult.getData()));
                getView().hideLoading();
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().showEmptyInfo("加载失败");
            }
        });
    }

    public void swipeToRefresh(Long homeworkId){
        getModel().getFileList(homeworkId, new OnHttpCallBack<RetResult<List<UploadFile>>>() {
            @Override
            public void onSuccess(RetResult<List<UploadFile>> listRetResult) {
                getView().initRecyclerView(transformData(listRetResult.getData()));
                getView().hideRefresh();
                DialogUtil.showToast(getView().getCurContext(),"刷新成功");
            }

            @Override
            public void onFailed(String errorMsg) {
                getView().showErrorMsg(errorMsg);
                getView().hideRefresh();
            }
        });
    }

    private List<CommonData> transformData(List<UploadFile> files){
        List<CommonData> commonDataList=new ArrayList<>();
        for (UploadFile file:files){
            CommonData commonData=new CommonData(null,file.getFileName(),(String)file.getUploader().get("name"),file.getId());
            commonData.setCustomText(file.getUploadTime());
            commonDataList.add(commonData);
        }
        return commonDataList;
    }

    public void uploadHeadImage(String filepath,Long homeworkId){
        QMUITipDialog loading= DialogUtil.showTipDialog(getView().getCurContext(),QMUITipDialog.Builder.ICON_TYPE_LOADING,"上传文件中",false);
        getModel().upload(filepath, homeworkId, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                loading.dismiss();
                getView().showInfo(QMUITipDialog.Builder.ICON_TYPE_SUCCESS,"上传成功");
                initFileList(homeworkId);
            }

            @Override
            public void onFailed(String errorMsg) {
                loading.dismiss();
                getView().showErrorMsg(errorMsg);
            }
        });
    }

    public void setRandomCode(String code,Long id){
        getModel().setRandomCode(code,id);
    }
}
