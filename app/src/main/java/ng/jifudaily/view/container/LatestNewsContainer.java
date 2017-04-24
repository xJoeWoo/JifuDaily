package ng.jifudaily.view.container;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import ng.jifudaily.R;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.ioc.service.AnimService;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.view.adapter.LatestNewsAdapter;

/**
 * Created by Ng on 2017/4/23.
 */

public class LatestNewsContainer extends Container<LatestNewsContainer> {

    public static final int NEWS_CLICK = 1;

    private LatestNewsAdapter adapter;
    private List<StoryEntity> latestNews;
    private int titleNewsIndex = 0;
    private final Random random = new Random(System.currentTimeMillis());

    @BindView(R.id.latest_rv)
    RecyclerView recyclerView;

    @BindView(R.id.container_latest_news_title_iv)
    ImageView iv;

    @BindView(R.id.container_latest_news_title_iv2)
    ImageView iv2;

    public LatestNewsContainer() {
        shouldDispose(false);
    }

    @Override
    protected void onBind(View v) {
        super.onBind(v);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LatestNewsAdapter(getActivity(), getPicasso());
        adapter.getObservable().subscribe(pos -> loadContent((int) pos));
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.container_latest_news_toolbar);
        getActivity().setSupportActionBar(toolbar);
    }

    @Override
    public void onAttached() {
        super.onAttached();
        changeTitleImage();
    }

    @OnClick({R.id.container_latest_news_title_iv, R.id.container_latest_news_title_iv2})
    void onTitleNewsClick() {
        loadContent(titleNewsIndex);
    }

    public LatestNewsContainer latestNews(List<StoryEntity> latestNews) {
        this.latestNews = latestNews;
        adapter.latestNews(latestNews);
        adapter.notifyDataSetChanged();
        changeTitleImage();
        return this;
    }

    private void loadContent(int pos) {
        publish(NEWS_CLICK, latestNews.get(pos));
    }

    private boolean usingIv2 = false;

    private AnimatorListenerAdapter notUsingIv2Adapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            iv2.setImageResource(android.R.color.transparent);
            iv2.setVisibility(View.GONE);
        }
    };
    private AnimatorListenerAdapter usingIv2Adapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            iv.setImageResource(android.R.color.transparent);
            iv.setVisibility(View.GONE);
        }
    };

    private Callback.EmptyCallback callback = new Callback.EmptyCallback() {
        @Override
        public void onSuccess() {
            if (usingIv2) {
                iv2.setVisibility(View.VISIBLE);
                AnimService.FadeIn(iv2, AnimService.DEFAULT_DURATION, usingIv2Adapter);
            } else {
                iv.setVisibility(View.VISIBLE);
                AnimService.FadeOut(iv2, AnimService.DEFAULT_DURATION, notUsingIv2Adapter);
            }
            getPicasso().cancelRequest(usingIv2 ? iv2 : iv);
            usingIv2 = !usingIv2;
        }
    };

    private void changeTitleImage() {
        if (latestNews == null || latestNews.get(0).getId() == StoryEntity.INVALID) {
            return;
        }
        getPicasso().load(latestNews.get(getRandomTitleNewsIndex()).getImages().get(0))
                .noFade()
                .into(usingIv2 ? iv2 : iv, callback);
    }

//    private void setAdapter() {
//        if (recyclerView != null) {
//            recyclerView.setAdapter(adapter);
//        }
//    }

    private int getRandomTitleNewsIndex() {
        int next = random.nextInt(latestNews.size());
        while (next == titleNewsIndex) {
            next = random.nextInt(latestNews.size());
        }
        titleNewsIndex = next;
        return titleNewsIndex;
    }

}
