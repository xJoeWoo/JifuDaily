package ng.jifudaily.view.container;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import ng.jifudaily.R;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.anim.AnimArgs;
import ng.jifudaily.support.util.anim.AnimUtil;
import ng.jifudaily.support.util.anim.Anims;
import ng.jifudaily.view.adapter.LatestNewsAdapter;

/**
 * Created by Ng on 2017/4/23.
 */

public class LatestNewsContainer extends Container<LatestNewsContainer> {

    public static final int NEWS_CLICK = 1;
    private final Random random = new Random(System.currentTimeMillis());
    @BindView(R.id.container_latest_news_rv)
    RecyclerView recyclerView;
    @BindView(R.id.container_latest_news_toolbar_iv)
    ImageView iv;
    @BindView(R.id.container_latest_news_toolbar_iv2)
    ImageView iv2;
    @BindView(R.id.container_latest_news_swipe)
    SwipeRefreshLayout refreshLayout;
    private LatestNewsAdapter latestNewsAdapter;
    private List<StoryEntity> latestNews;
    private int titleNewsIndex = 0;
    private boolean usingIv2 = false;
    private boolean firstOpen = true;
    private Callback.EmptyCallback callback = new Callback.EmptyCallback() {
        @Override
        public void onSuccess() {

            if (usingIv2) {

                AnimUtil.with(iv2).using(Anims.FadeIn)
                        .onStart(x -> iv2.setVisibility(View.VISIBLE))
                        .onEnd(x -> {
                            iv.setImageResource(android.R.color.transparent);
                            iv.setVisibility(View.GONE);
                        })
                        .start();
            } else {
                AnimArgs args = AnimUtil.with(iv2).using(Anims.FadeOut)
                        .onStart(x -> iv.setVisibility(View.VISIBLE))
                        .onEnd(x -> {
                            iv2.setVisibility(View.GONE);
                            iv2.setImageResource(android.R.color.transparent);
                        });

                if (firstOpen) {
                    args.with(iv).using(Anims.FadeIn);
                    firstOpen = false;
                }

                args.start();

            }

            getServices().net().picasso().cancelRequest(usingIv2 ? iv2 : iv);
            usingIv2 = !usingIv2;
        }
    };

    public LatestNewsContainer() {
        setShouldDispose(false);
    }

    @Override
    protected void onViewCreated(View v) {
        super.onViewCreated(v);
        latestNewsAdapter = new LatestNewsAdapter(getActivity(), getServices().net().picasso());
        latestNewsAdapter.getObservable().subscribe(pos -> publishLoadContent((int) pos));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        long duration = AnimUtil.DEFAULT_DURATION / 2;
        recyclerView.getItemAnimator().setAddDuration(duration);
        recyclerView.getItemAnimator().setChangeDuration(duration);
        recyclerView.getItemAnimator().setRemoveDuration(duration);
        recyclerView.getItemAnimator().setMoveDuration(duration);
        recyclerView.setAdapter(latestNewsAdapter);
        refreshLayout.setOnRefreshListener(this::loadLatestNews);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
    }

    @Override
    public void onAttached() {
        super.onAttached();

        getManager().getActivity().setSupportActionBar((Toolbar) getContainingView().findViewById(R.id.container_latest_news_toolbar));
    }

    @Override
    protected void onViewDrawn(View v) {
        super.onViewDrawn(v);
    }

    @OnClick({R.id.container_latest_news_toolbar_iv, R.id.container_latest_news_toolbar_iv2})
    void onTitleNewsClick() {
        publishLoadContent(titleNewsIndex);
    }

    private void publishLoadContent(int pos) {
        publish(NEWS_CLICK, latestNews.get(pos));
    }

    @Override
    public Transition getExitTransition() {
        return TransitionInflater.from(getActivity()).inflateTransition(R.transition.container_latest_news_exit);
    }

    @Override
    public Transition getReenterTransition() {
        return TransitionInflater.from(getActivity()).inflateTransition(R.transition.container_latest_news_reenter);
    }

    public LatestNewsContainer loadLatestNews() {
        refreshLayout.setRefreshing(true);
        getServices().daily().getLatestNews().observeOn(AndroidSchedulers.mainThread()).subscribe(entity -> {
            processLatestNews(entity.getStories());
        }, error -> {
        });
        return this;
    }

    private void processLatestNews(List<StoryEntity> entities) {
        refreshLayout.setRefreshing(false);

        this.latestNews = entities;
        latestNewsAdapter.latestNews(new ArrayList<>(entities));
        recyclerView.smoothScrollToPosition(0);
        changeTitleImage();
    }

    private void changeTitleImage() {
        if (latestNews == null || latestNews.size() == 0 || latestNews.get(0).getId() == StoryEntity.INVALID) {
            return;
        }
        StoryEntity s = latestNews.get(getRandomTitleNewsIndex());
        if (s.getImages() == null || s.getImages().size() == 0) {
            return;
        }
        getServices().net().picasso().load(s.getImages().get(0)).noFade().into(usingIv2 ? iv2 : iv, callback);
//        getServices().net().picasso().load(s.getImages().get(0)).noFade().into(iv,callback);
//        usingIv2 = !usingIv2;
    }


    private int getRandomTitleNewsIndex() {
        if (latestNews.size() < 2) {
            return 0;
        }
        int next = random.nextInt(latestNews.size());
        while (next == titleNewsIndex) {
            next = random.nextInt(latestNews.size());
        }
        titleNewsIndex = next;
        return titleNewsIndex;
    }

}
