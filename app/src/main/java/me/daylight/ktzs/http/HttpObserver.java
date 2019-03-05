package me.daylight.ktzs.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class HttpObserver<T> implements Observer<T> {
    private OnHttpCallBack callBack;
    protected HttpObserver(OnHttpCallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        HttpFeedBackUtil.handleRetResult((RetResult) t,callBack);
    }

    @Override
    public void onError(Throwable e) {
        HttpFeedBackUtil.handleException(e,callBack);
    }

    @Override
    public void onComplete() {

    }
}
