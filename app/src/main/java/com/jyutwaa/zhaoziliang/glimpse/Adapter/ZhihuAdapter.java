package com.jyutwaa.zhaoziliang.glimpse.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.MainActivity;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDailyItem;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Utils.DBUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ViewUtils;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.BadgedFourThreeImageView;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.DribbbleTarget;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.ObservableColorMatrix;
import com.jyutwaa.zhaoziliang.glimpse.ZhihuDetailPageActivity;

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
    private Context mContext;
    private boolean isLoading;


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
                return new NormalViewHolder(View.inflate(mContext, R.layout.zhihu_item_layout, null));
            case TYPE_LOADING_MORE:
                return new LoadingMoreViewHolder(View.inflate(mContext, R.layout.loading_more_layout, null));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch(viewType){
            case TYPE_NORMAL:
                bindNormalViewHolder((NormalViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingMoreViewHolder((LoadingMoreViewHolder) holder, position);
                break;
            default:
                break;
        }
    }

    private void bindLoadingMoreViewHolder(LoadingMoreViewHolder holder, int position) {
        holder.progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    private void bindNormalViewHolder(final NormalViewHolder holder, int position) {
        final ZhihuDailyItem zhihuDailyItem = mZhihuDailyItems.get(holder.getAdapterPosition());
        // TODO:check if 2nd parameter position equals with holder.getAdapterPosition()?
        if(DBUtils.getDB(mContext).isRead(Config.ZHIHU, zhihuDailyItem.getId(), 1)){
            holder.itemText.setTextColor(Color.GRAY);
        } else {
            holder.itemText.setTextColor(Color.BLACK);
        }
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入知乎详情页", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemText.setText(zhihuDailyItem.getTitle());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "进入知乎详情页", Toast.LENGTH_SHORT).show();
                enterDetailPageActivity(holder, zhihuDailyItem);
            }
        });

        Glide.with(mContext)
                .load(mZhihuDailyItems.get(position).getImages()[0])
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!zhihuDailyItem.hasFadedIn) {
                            holder.itemImage.setHasTransientState(true);
                            final ObservableColorMatrix cm = new ObservableColorMatrix();
                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    holder.itemImage.setColorFilter(new ColorMatrixColorFilter(cm));
                                }
                            });
                            animator.setDuration(2000L);
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.itemImage.clearColorFilter();
                                    holder.itemImage.setHasTransientState(false);
                                    animator.start();
                                    zhihuDailyItem.hasFadedIn = true;

                                }
                            });
                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(widthPx, heightPx)
                .into(new DribbbleTarget(holder.itemImage, false));
    }

    @Override
    public int getItemCount() {
        return mZhihuDailyItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position < getItemCount() && getItemCount() > 0){
            return TYPE_NORMAL;
        } else {
            return TYPE_LOADING_MORE;
        }
    }

    /**
     * parameter in notifyItemInsert(int position) will not increase automatically
     * and when another item is inserted the original item's  position has been pushed back for 1
     */
    private int getLoadingMoreItemCount(){
        return isLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    @Override
    public void loadingStart() {
        if(isLoading){
            return;
        }
        isLoading = true;
        notifyItemInserted(getLoadingMoreItemCount());
    }

    @Override
    public void loadingfinish() {
        if(!isLoading){
            return;
        }
        int loadingPos = getLoadingMoreItemCount();
        isLoading = false;
        notifyItemRemoved(loadingPos);
    }

    private void enterDetailPageActivity(RecyclerView.ViewHolder viewHolder, ZhihuDailyItem item) {
        DBUtils.getDB(mContext).addHasReadInfo(Config.ZHIHU, item.getId(), 1);
        NormalViewHolder holder = (NormalViewHolder) viewHolder;
        holder.itemText.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, ZhihuDetailPageActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        mContext.startActivity(intent);
    }

    public void clearData() {
        mZhihuDailyItems.clear();
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<ZhihuDailyItem> stories) {
        mZhihuDailyItems.addAll(stories);
        notifyDataSetChanged();
    }

    private static class NormalViewHolder extends RecyclerView.ViewHolder{
        TextView itemText;
        BadgedFourThreeImageView itemImage;
        LinearLayout itemLayout;

        NormalViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView) itemView.findViewById(R.id.zhihu_item_text);
            itemImage = (BadgedFourThreeImageView) itemView.findViewById(R.id.zhihu_item_image);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.ll_zhihu_item);
        }
    }

    private static class LoadingMoreViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        LoadingMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }
}