package me.daylight.ktzs.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.daylight.ktzs.R;
import me.daylight.ktzs.customview.CommonListItemView;
import me.daylight.ktzs.entity.CommonData;

/**
 * @author Daylight
 * @date 2019/03/10 22:17
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {

    private Context context;

    private List<CommonData> data;

    private int orientation;

    private int ACCESSORY_TYPE;

    private int height;

    private OnCommonItemClickListener onCommonItemClickListener;

    public CommonAdapter(Context context) {
        this.context = context;
        this.orientation = QMUICommonListItemView.HORIZONTAL;
        this.height= QMUIResHelper.getAttrDimen(context, R.attr.qmui_list_item_height);
        this.ACCESSORY_TYPE= QMUICommonListItemView.ACCESSORY_TYPE_NONE;
        setHasStableIds(true);
    }

    public CommonAdapter(Context context, int orientation) {
        this.context = context;
        this.orientation = orientation;
        this.height= QMUIResHelper.getAttrDimen(context, R.attr.qmui_list_item_height);
        this.ACCESSORY_TYPE= QMUICommonListItemView.ACCESSORY_TYPE_NONE;
        setHasStableIds(true);
    }

    public CommonAdapter(Context context, int orientation,int ACCESSORY_TYPE) {
        this.context = context;
        this.orientation = orientation;
        this.height=QMUIResHelper.getAttrDimen(context, R.attr.qmui_list_item_height);
        this.ACCESSORY_TYPE= ACCESSORY_TYPE;
        setHasStableIds(true);
    }

    public CommonAdapter(Context context, int orientation,int ACCESSORY_TYPE,int height) {
        this.context = context;
        this.orientation = orientation;
        this.height=height;
        this.ACCESSORY_TYPE=ACCESSORY_TYPE;
        setHasStableIds(true);
    }

    public void addData(CommonData commonData){
        data.add(commonData);
        notifyItemInserted(data.size()-1);
    }

    public void setData(List<CommonData> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public List<CommonData> getData(){
        return data;
    }

    public void setOnCommonItemClickListener(OnCommonItemClickListener listener){
        this.onCommonItemClickListener= listener;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_common,parent,false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        if (data!=null){
            CommonData commonData=data.get(position);
            holder.itemView.setOrientation(orientation);
            holder.itemView.setText(commonData.getText());
            holder.itemView.setDetailText(commonData.getSubText());
            holder.itemView.setAccessoryType(ACCESSORY_TYPE);
            holder.itemView.setHeight(height);
            if (commonData.getImage()!=null)
                holder.itemView.setImage(commonData.getImage().getDrawable(),commonData.getImage().getWidth(),commonData.getImage().getHeight());
            if (ACCESSORY_TYPE==QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM)
                holder.initTextView(context,commonData.getCustomText());
            if (onCommonItemClickListener!=null)
                holder.itemView.setOnClickListener(v -> onCommonItemClickListener.onItemClick(holder.itemView,position));
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    static class CommonViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.common_item)
        CommonListItemView itemView;

        TextView textView;

        CommonViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this,view);
            itemView.getTextView().setMaxLines(1);
        }

        void initTextView(Context context,String text){
            TextView textView=new TextView(context);
            textView.setWidth(QMUIDisplayHelper.dp2px(context,150));
            textView.setGravity(Gravity.END);
            textView.setTextSize(14);
            textView.setTextColor(QMUIResHelper.getAttrColor(context,R.attr.qmui_config_color_gray_5));
            textView.setText(text);
            itemView.addAccessoryCustomView(textView);
        }
    }

    public interface OnCommonItemClickListener {
        void onItemClick(View view, int position);
    }
}
