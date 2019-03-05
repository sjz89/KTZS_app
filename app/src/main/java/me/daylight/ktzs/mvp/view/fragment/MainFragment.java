package me.daylight.ktzs.mvp.view.fragment;


import com.qmuiteam.qmui.arch.QMUIFragment;

import me.daylight.ktzs.R;
import me.daylight.ktzs.mvp.presenter.BasePresenter;

public class MainFragment extends BaseFragment {


    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return QMUIFragment.SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    protected void popBackStack() {
        getCurContext().moveTaskToBack(true);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void doAfterView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
