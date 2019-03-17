package me.daylight.ktzs.mvp.model;

import android.content.Context;

public abstract class BaseModel {
    private Context context;

    public void init(){

    };

    public void setContext(Context context){
        this.context=context;
        init();
    }

    public Context getContext() {
        return context;
    }
}
