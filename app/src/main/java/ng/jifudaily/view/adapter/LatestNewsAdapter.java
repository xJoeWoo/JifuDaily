package ng.jifudaily.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import ng.jifudaily.R;
import ng.jifudaily.support.ioc.service.AnimService;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.CircleTransform;
import ng.jifudaily.support.util.ObservableRecyclerViewAdapter;
import ng.jifudaily.support.util.ViewHolderClickListener;
import ng.jifudaily.view.base.SquareImageView;

/**
 * Created by Ng on 2017/4/23.
 */

public class LatestNewsAdapter extends ObservableRecyclerViewAdapter<LatestNewsAdapter.LatestNewsViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<StoryEntity> latestNews;
    private CircleTransform circleTransform = new CircleTransform();
    private ViewHolderClickListener onClickListener = (v, pos) -> publish(pos);
    private Picasso picasso;

    public LatestNewsAdapter(Context context, Picasso picasso) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.picasso = picasso;
    }

    public LatestNewsAdapter latestNews(List<StoryEntity> latestNews) {
        this.latestNews = latestNews;
        return this;
    }

    @Override
    public LatestNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LatestNewsViewHolder(layoutInflater.inflate(R.layout.item_latest_news, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(LatestNewsViewHolder holder, int position) {
        StoryEntity e = latestNews.get(position);
        if (e.getId() == StoryEntity.INVALID) {
            return;
        }

        if (holder.getHolder().getVisibility() != View.GONE) {
            AnimService.FadeOut(holder.getHolder(), 300, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    holder.getHolder().setVisibility(View.GONE);
                }
            });
        }
        holder.getTv().setText(e.getTitle());
        picasso.load(latestNews.get(position).getImages().get(0))
                .error(R.drawable.ic_imageholder)
                .placeholder(R.drawable.ic_imageholder)
                .transform(circleTransform)
                .into(holder.getIv());
    }

    @Override
    public int getItemCount() {
        return latestNews == null ? 0 : latestNews.size();
    }

    static class LatestNewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_latest_news_iv)
        SquareImageView iv;

        @BindView(R.id.item_latest_news_tv)
        TextView tv;

        @BindView(R.id.item_latest_news_tv_holder)
        View holder;

        LatestNewsViewHolder(View view, ViewHolderClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v ->
                    listener.onClick(v, getAdapterPosition()));
        }

        public View getHolder() {
            return holder;
        }

        public SquareImageView getIv() {
            return iv;
        }

        public TextView getTv() {
            return tv;
        }
    }

}
