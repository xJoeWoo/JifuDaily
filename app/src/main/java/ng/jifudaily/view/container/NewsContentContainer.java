package ng.jifudaily.view.container;

import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import ng.jifudaily.R;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.ioc.service.AnimUtil;
import ng.jifudaily.support.net.entity.NewsContentEntity;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.DpUtil;
import ng.jifudaily.support.util.FadeInCallback;
import ng.jifudaily.support.util.TransitionListenerAdapter;
import ng.jifudaily.view.base.AppbarImageView;


/**
 * Created by Ng on 2017/4/24.
 */

public class NewsContentContainer extends Container<NewsContentContainer> {

    private final SparseIntArray positions = new SparseIntArray();
    @BindView(R.id.container_news_content_appbar_ly)
    AppBarLayout appBar;
    @BindView(R.id.container_news_content_wv)
    WebView wv;
    @BindView(R.id.container_news_content_pb)
    ProgressBar pb;
    @BindView(R.id.container_news_content_toolbar_iv)
    AppbarImageView iv;
    @BindView(R.id.container_news_content_toolbar_iv2)
    AppbarImageView iv2;
    @BindView(R.id.container_news_content_tv)
    TextView tvTitle;
    @BindView(R.id.container_news_content_toolbar)
    Toolbar tb;
    @BindView(R.id.container_news_content_blocker)
    View blocker;

    //    @BindView(R.id.container_news_content_pbi)
//    ProgressBar pbi;
    @BindView(R.id.container_news_content_blocker_bg)
    View blockerBg;
    @BindView(R.id.container_news_content_holder)
    View holder;
    @BindView(R.id.container_news_content_pbi_bg)
    View pgiBg;
    @BindView(R.id.container_news_content_wrapper)
    NestedScrollView wrapper;
    private boolean appBarLayoutCollapsed;
    private boolean appbarLayoutExpended;
    private NewsContentEntity news;
    private StoryEntity story;
    private int actionBarHeight = 0;

    public NewsContentContainer() {
        setRequestNewView(true);
    }

    @Override
    public void disposeView() {
        super.disposeView();
    }

    @Override
    protected void onViewCreated(View v) {
        super.onViewCreated(v);
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);

            }
        });
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (isAttached()) {
                    if (news != null) {
                        AnimUtil.object(wrapper, "scrollY", new AnimUtil.ObjectArgs().duration(AnimUtil.DEFAULT_DURATION * 2).interpolator(AnimUtil.ACCELERATE_DECELERATE_INTERPOLATOR), 0, positions.get(news.getId()));
                    }
                    AnimUtil.fadeOut(pb, AnimUtil.DEFAULT_DURATION / 2, null);
                    AnimUtil.fadeOut(pgiBg, AnimUtil.DEFAULT_DURATION / 2, null);
                }
            }
        });

        TypedValue tv = new TypedValue();
        if (getManager().getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getManager().getActivity().getResources().getDisplayMetrics());
        }

        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int o = Math.abs(verticalOffset);
            appBarLayoutCollapsed = o == appBarLayout.getTotalScrollRange() || o >= actionBarHeight;
            appbarLayoutExpended = o == 0;
        });

        AppCompatActivity act = getManager().getActivity();
        act.setSupportActionBar(tb);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onViewDrawn(View v) {
        super.onViewDrawn(v);
        appBar.setExpanded(true);
    }

    @Override
    public void onAttached() {
        super.onAttached();
        if (story != null) {
            tvTitle.setText(story.getTitle());
            getServices().daily().getNewsContent(story.getId()).subscribe(NewsContentContainer.this::news, error -> {
            });
        }
    }


    @Override
    public void onDetached() {
        super.onDetached();
        wv.stopLoading();
    }

    @Override
    public Transition getEnterTransition() {
        Transition t = TransitionInflater.from(getContext()).inflateTransition(R.transition.container_news_content_enter);
        t.addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionStart(Transition transition) {
                super.onTransitionStart(transition);
                MotionEvent motionEvent = getManager().getActivity().getLatestMotion();
                AnimUtil.circularReveal(blocker, (int) motionEvent.getX(), (int) motionEvent.getY(), true, (int) (AnimUtil.DEFAULT_DURATION), new AnimUtil.ObjectArgs().delay(150).end(x -> {
                    AnimUtil.fadeOut(blockerBg, AnimUtil.DEFAULT_DURATION, new AnimUtil.ObjectArgs().end(xx -> blockerBg.setVisibility(View.GONE)));
                }));
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                t.removeListener(this);
            }
        });
        return t;
    }

    @Override
    public Transition getExitTransition() {
        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.container_news_content_exit);
        if (appBarLayoutCollapsed) {
            transition.excludeTarget(R.id.container_news_content_tv, true);
        }
        return transition;
    }

    public NewsContentContainer load(StoryEntity story) {
        this.story = story;
        return this;
    }

    public NewsContentContainer news(NewsContentEntity news) {
        this.news = news;
        wv.loadData(news.getCompleteHTML(), "text/html; charset=UTF-8", null);
        getServices().net().picasso().load(this.news.getImageUrl()).noFade().into(iv, FadeInCallback.createInstance(iv));
        return this;
    }

    @Override
    public void dispose() {
        super.dispose();
        story = null;
        news = null;
    }

    @Override
    public boolean onBackPressed() {

        if (news != null) {
            positions.put(news.getId(), wrapper.getScrollY());
        }

        if (appbarLayoutExpended) {

            // imageview in appbar layout will disappear when change scene, so 麻烦
            if (!appBarLayoutCollapsed) {
                Rect r = new Rect();
                iv.getLocalVisibleRect(r);
                iv2.setVisibility(View.VISIBLE);
                iv2.setImageDrawable(iv.getDrawable());
                iv2.setY(r.top);
            }

            if (wrapper.getScrollY() <= DpUtil.dpToPx(200)) {
                return false;
            }
        }

        AnimUtil.object(wrapper, "scrollY", new AnimUtil.ObjectArgs().duration(AnimUtil.DEFAULT_DURATION).interpolator(AnimUtil.DECELERATE_INTERPOLATOR).end(x -> getManager().getSwitcher().back()), wrapper.getScrollY(), 0);
        return true;
    }
}
