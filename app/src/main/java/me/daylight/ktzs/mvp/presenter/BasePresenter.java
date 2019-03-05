package me.daylight.ktzs.mvp.presenter;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import me.daylight.ktzs.mvp.model.BaseModel;
import me.daylight.ktzs.mvp.view.BaseView;


public class BasePresenter<V extends BaseView, M extends BaseModel> {
    private WeakReference<V> mView;
    private M mModel; // 动态创建的 model 的对象

    @SuppressWarnings("unchecked")
    public void attach(WeakReference<V> mView) {
        this.mView = mView;
        // 最好要判断一下类型
        Type[] params = ((ParameterizedType) Objects.requireNonNull(this.getClass().getGenericSuperclass())).getActualTypeArguments();
        try {
            mModel = (M) ((Class) params[1]).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        mModel.setContext(mView.get().getCurContext());
    }

    //内存泄漏解决
    public void detach() {
        if (mView == null) return;
        this.mView.clear();
        this.mView = null;
    }

    //获取view对象
    public V getView() {
        if (mView == null) return null;
        return mView.get();
    }

    //获取model对象
    public M getModel() {
        return mModel;
    }
}