package me.daylight.ktzs.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import me.daylight.ktzs.R;
import me.daylight.ktzs.app.GlideApp;

/**
 * @author Daylight
 * @date 2019/03/07 13:56
 */
public class CommonListItemView extends QMUICommonListItemView {
    private Context context;

    public CommonListItemView(Context context) {
        super(context);
        this.context=context;
    }

    public CommonListItemView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public CommonListItemView(Context context,int drawable,int width,int height,int orientation,int accessoryType){
        super(context);
        this.context=context;

        mImageView.setVisibility(VISIBLE);
        GlideApp.with(context).load(drawable).override(width,height).into(mImageView);

        int mHeight;
        if (orientation == QMUICommonListItemView.VERTICAL) {
            mHeight = QMUIResHelper.getAttrDimen(getContext(), R.attr.qmui_list_item_height_higher);
        } else {
            mHeight = QMUIResHelper.getAttrDimen(getContext(), R.attr.qmui_list_item_height);
        }
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHeight));

        setOrientation(orientation);
        setAccessoryType(accessoryType);
    }

    public void setHeight(int height){
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
    }

    public void setImage(Drawable drawable,int width,int height){
        mImageView.setVisibility(VISIBLE);
        GlideApp.with(context).load(drawable).override(width, height).into(mImageView);
    }
}
