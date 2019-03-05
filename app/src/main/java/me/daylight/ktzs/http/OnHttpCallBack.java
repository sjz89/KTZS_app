package me.daylight.ktzs.http;

public interface OnHttpCallBack<T> {
    void onSuccess(T t);

    void onFailed(String errorMsg);
}
