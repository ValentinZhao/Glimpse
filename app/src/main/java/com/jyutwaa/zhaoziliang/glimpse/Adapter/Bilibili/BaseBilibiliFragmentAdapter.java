package com.jyutwaa.zhaoziliang.glimpse.Adapter.Bilibili;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ViewUtils;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.BadgedFourThreeImageView;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.DribbbleTarget;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.ObservableColorMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoziliang on 17/3/2.
 */

public class BaseBilibiliFragmentAdapter extends RecyclerView.Adapter {
    private static final int TYPE_NORMAL = 1;

    private static final int TYPE_LOADING = -1;

    private Context mContext;
    private List<TopListTypeItem> mTopListTypeItems = new ArrayList<>();
    private float width;
    private int widthPx;
    private int heightPx;
    private String videoUrl;


    public BaseBilibiliFragmentAdapter(Context mContext) {
        this.mContext = mContext;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = ViewUtils.dp2px(mContext, width);
        heightPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return new IntegratedAdapter.NormalViewHolder(View.inflate(mContext, R.layout.bilibili_item_layout, null));
            case TYPE_LOADING:
                return new IntegratedAdapter.LoadingViewHolder(View.inflate(mContext, R.layout.loading_more_layout, null));
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
                bindNormalViewHolder((IntegratedAdapter.NormalViewHolder) holder, position);
                break;
            case TYPE_LOADING:
                bindLoadingViewHolder((IntegratedAdapter.LoadingViewHolder) holder, position);
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
        if(position < getItemCount() && getItemCount() > 0){
            return TYPE_NORMAL;
        } else {
            return TYPE_LOADING;
        }
    }

    private void bindNormalViewHolder(final IntegratedAdapter.NormalViewHolder holder, int position) {
        final TopListTypeItem item = mTopListTypeItems.get(holder.getAdapterPosition());
        videoUrl = item.getVideoUrl();
//        if(DBUtils.getDB(mContext).isRead(Config.BILIBILI, item.getAid(), 1)){
//            holder.itemTitle.setTextColor(Color.GRAY);
//        } else {
//            holder.itemTitle.setTextColor(Color.BLACK);
//        }
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入视频详情页", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入视频详情页", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入评论区", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemTitle.setText(item.getTitle());
        holder.itemDescription.setText(item.getDescription());
        holder.itemAuthor.setText(item.getAuthor());
        holder.itemFavorite.setText(item.getFavoritesCount());
        holder.itemComment.setText(item.getCommentCount());
        Glide.with(mContext)
                .load(mTopListTypeItems.get(position).getIconUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!item.hasFadedIn) {
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
                                    item.hasFadedIn = true;

                                }
                            });
                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(widthPx, heightPx)
                .into(new DribbbleTarget(holder.itemImage, false));
    }

    private void bindLoadingViewHolder(IntegratedAdapter.LoadingViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
    }

    public void clearData(){
        mTopListTypeItems.clear();
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<TopListTypeItem> items){
        mTopListTypeItems.addAll(items);
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
