package com.jyutwaa.zhaoziliang.glimpse.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jyutwaa.zhaoziliang.glimpse.MainActivity;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDailyItem;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/2/9.
 */

public class ZhihuAdapter extends RecyclerView.Adapter implements MainActivity.LoadingMore{

    private static final int TYPE_NORMAL = 1;

    private static final int TYPE_LOADING_MORE = -1;

    private ArrayList<ZhihuDailyItem> mZhihuDailyItems = new ArrayList<>();
    private float width;
    private int widthPx;
    private int heightPx;
    private boolean isLoadingMore;
    private Context mContext;


    public ZhihuAdapter(Context context){
        mContext = context;
        width = context.getResources().getDimension(R.dimen.image_width);
        widthPx = ViewUtils.dp2px(context, width);
        heightPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case TYPE_NORMAL:
                break;
            case TYPE_LOADING_MORE:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void loadingStart() {

    }

    @Override
    public void loadingfinish() {

    }

    public void clearData() {

    }

    public void addItems(ArrayList<ZhihuDailyItem> stories) {
        mZhihuDailyItems.addAll(stories);
        notifyDataSetChanged();
    }

    static class NormalViewHolder{

    }
}