package com.jyutwaa.zhaoziliang.glimpse.Adapter.Bilibili;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ViewUtils;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.BadgedFourThreeImageView;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/3/1.
 */

public class CoverAdapter extends RecyclerView.Adapter {
    private static final int TYPE_NORMAL = 1;

    private static final int TYPE_LOADING = -1;

    private Context mContext;
    private ArrayList<TopListTypeItem> mTopListTypeItems;

    private int widthPx;
    private int heightPx;
    private float width;

    public CoverAdapter(Context mContext) {
        this.mContext = mContext;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = ViewUtils.dp2px(mContext, width);
        heightPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case TYPE_NORMAL:
                return new NormalViewHolder(View.inflate(mContext, R.layout.bilibili_item_layout, null));
            case TYPE_LOADING:
                return new LoadingViewHolder(View.inflate(mContext, R.layout.loading_more_layout, null));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType){
            case TYPE_NORMAL:
                bindNormalViewHolder((NormalViewHolder) holder, position);
                break;
            case TYPE_LOADING:
                bindLoadingViewHolder((LoadingViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTopListTypeItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemCount() > 0 && position < getItemCount()){
            return TYPE_NORMAL;
        } else {
            return TYPE_LOADING;
        }
    }

    private void bindLoadingViewHolder(LoadingViewHolder holder, int position) {

    }

    private void bindNormalViewHolder(NormalViewHolder holder, int position) {

    }

    public void addItems(ArrayList<TopListTypeItem> items){
        mTopListTypeItems = items;
        notifyDataSetChanged();
    }

    public void clearData(){
        mTopListTypeItems.clear();
        notifyDataSetChanged();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder{

        BadgedFourThreeImageView itemImage;
        TextView itemTitle;
        TextView itemDescription;
        TextView itemAuthor;
        TextView itemFavorite;
        TextView itemComment;

        public NormalViewHolder(View itemView) {
            super(itemView);
            itemImage = (BadgedFourThreeImageView) itemView.findViewById(R.id.bilibili_item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.bilibili_item_title);
            itemDescription = (TextView) itemView.findViewById(R.id.bilibili_item_description);
            itemAuthor = (TextView) itemView.findViewById(R.id.tv_bilibili_item_author);
            itemFavorite = (TextView) itemView.findViewById(R.id.tv_bilibili_item_favorite);
            itemComment = (TextView) itemView.findViewById(R.id.tv_bilibili_item_comment);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }
}
