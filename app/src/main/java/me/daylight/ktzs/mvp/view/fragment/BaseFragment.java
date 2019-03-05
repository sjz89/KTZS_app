package me.daylight.ktzs.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import me.daylight.ktzs.mvp.presenter.BasePresenter;
import me.daylight.ktzs.mvp.view.BaseView;


public abstract class BaseFragment<P extends BasePresenter> extends QMUIFragment implements BaseView {
    private P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(new WeakReference(this));
        }
    }

    protected abstract int initLayoutId();

    protected abstract void doAfterView();

    @Override
    public View onCreateView() {
        View view=LayoutInflater.from(getBaseFragmentActivity()).inflate(initLayoutId(),null);
        ButterKnife.bind(this,view);
        doAfterView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (QMUIKeyboardHelper.isKeyboardVisible(getBaseFragmentActivity()))
            QMUIKeyboardHelper.hideKeyboard(getView());
    }

    // 由子类去实现创建
    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter==null) return;
        mPresenter.detach();
    }

    @Override
    public QMUIFragmentActivity getCurContext() {
        return getBaseFragmentActivity();
    }

    public P getPresenter() {
        return mPresenter;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

}
