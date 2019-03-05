package me.daylight.ktzs.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class DialogUtil {
    public static QMUITipDialog showTipDialog(Context context, int type, String msg, boolean delayDismiss){
        QMUITipDialog dialog=new QMUITipDialog.Builder(context)
                .setIconType(type)
                .setTipWord(msg)
                .create(true);
        dialog.show();
        if (delayDismiss)
            new Handler().postDelayed(dialog::dismiss,1500);
        return dialog;
    }
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
