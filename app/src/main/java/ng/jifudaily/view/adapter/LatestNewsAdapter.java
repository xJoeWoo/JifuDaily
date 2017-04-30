package ng.jifudaily.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import ng.jifudaily.R;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.CircleTransform;
import ng.jifudaily.support.util.adapter.ObservableRecyclerViewAdapter;
import ng.jifudaily.support.util.adapter.ViewHolderClickListener;
import ng.jifudaily.view.base.SquareImageView;

/**
 * Created by Ng on 2017/4/23.
 */

public class LatestNewsAdapter extends ObservableRecyclerViewAdapter<LatestNewsAdapter.LatestNewsViewHolder, StoryEntity> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private CircleTransform circleTransform = new CircleTransform();
    private ViewHolderClickListener onClickListener = (v, pos) -> publish(pos);
    private Picasso picasso;
    private Disposable sub;
    private List<StoryEntity> latestNews;

    public LatestNewsAdapter(Context context, Picasso picasso) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.picasso = picasso;
    }

    public LatestNewsAdapter latestNews(List<StoryEntity> entities) {
        if (latestNews == null) {
            updateRange(0, entities);
        } else {
            if (entities.size() > latestNews.size()) {
                int end = entities.size() - latestNews.size();
                addRange(0, entities.subList(0, end));
            } else if (entities.size() < latestNews.size()) {
                replace(entities);
            }
        }
        latestNews = entities;
        return this;
    }

    public LatestNewsAdapter anim() {
        List<StoryEntity> data = getData();
        clear();
        addRange(0, data);
        return this;
    }

//    public LatestNewsAdapter anim() {
//        if (sub != null) {
//            sub.dispose();
//            sub = null;
//        }
//        sub = Observable.intervalRange(0,
//                latestNews.size(),
//                0,
//                AnimUtil.DEFAULT_DURATION,
//                TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(x -> {
//                    notifyItemChanged(x.intValue());
//                });
//        return this;
//    }

    @Override
    public LatestNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LatestNewsViewHolder(layoutInflater.inflate(R.layout.item_latest_news, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(LatestNewsViewHolder holder, int position) {

        StoryEntity e = getData().get(position);

        holder.getTv().setText(e.getTitle());

        if (e.getId() == StoryEntity.INVALID) {
            return;
        }

        if (e.getImages() != null && e.getImages().size() > 0) {
            picasso.load(e.getImages().get(0))
                    .error(R.drawable.ic_image_holder)
                    .placeholder(R.drawable.ic_image_holder)
                    .transform(circleTransform)
                    .into(holder.getIv());
        }

    }


    static class LatestNewsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_latest_news_iv)
        SquareImageView iv;

        @BindView(R.id.item_latest_news_holder)
        TextView tv;


        @BindView(R.id.item_latest_news_scene_container)
        ViewGroup rootView;

        LatestNewsViewHolder(View view, ViewHolderClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v ->
                    listener.onClick(v, getAdapterPosition()));
        }

        public SquareImageView getIv() {
            return iv;
        }

        public TextView getTv() {
            return tv;
        }


    }

}
