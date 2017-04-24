package ng.jifudaily.view.container;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ng.jifudaily.R;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.ioc.service.AnimService;
import ng.jifudaily.support.net.entity.NewsContentEntity;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.FadeInCallback;


/**
 * Created by Ng on 2017/4/24.
 */

public class NewsContentContainer extends Container<NewsContentContainer> {

    private final String mimeType = "text/html; charset=UTF-8";
    private final String encoding = "UTF-8";

    private final SparseIntArray positions = new SparseIntArray();

    private boolean loadComplete = false;

    private final WebChromeClient client = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {
                progressBar.setProgress(newProgress);
                return;
            }
            if (loadComplete) {
                return;
            }
            loadComplete = true;
            Observable.timer(20, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(x -> {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                AnimService.FadeIn(webView, AnimService.DEFAULT_DURATION, null);
                webView.scrollTo(0, positions.get(news.getId()));
            });
        }

        //        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            webView.scrollTo(0, 0);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            int a = positions.get(news.getId());
////            webView.scrollTo(0, a);
//            Observable.timer(10, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(x -> webView.scrollTo(0, a));
//        }
    };


    private NewsContentEntity news;

    @BindView(R.id.container_news_content_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.container_news_content_wv)
    WebView webView;

    @BindView(R.id.container_news_content_pb)
    ProgressBar progressBar;

    @BindView(R.id.container_news_content_iv)
    ImageView imageView;

    @BindView(R.id.container_news_content_tv)
    TextView tvTitle;

    @BindView(R.id.container_news_content_toolbar)
    Toolbar tb;

    public NewsContentContainer() {
        shouldDispose(false);
    }

    @Override
    protected void onBind(View v) {
        super.onBind(v);
        webView.setWebChromeClient(client);
        webView.getSettings().setDefaultTextEncodingName(encoding);
        webView.setNestedScrollingEnabled(false);
        reset("");
    }

    @Override
    public void onAttached() {
        super.onAttached();
        AppCompatActivity act = getActivity();
        act.setSupportActionBar(tb);
        act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        act.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public NewsContentContainer load(StoryEntity story) {
        reset(story.getTitle());
        getActivity().getServices().daily().getNewsContent(story.getId()).subscribe(this::news, error -> {
        });
        return this;
    }

    public NewsContentContainer news(NewsContentEntity news) {
        this.news = news;
        reset(this.news.getTitle());
        loadComplete = false;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        webView.loadData(news.getCompleteHTML(), mimeType, null);
        getPicasso().load(this.news.getImageUrl()).noFade().into(imageView, FadeInCallback.createInstance(imageView));
        appBarLayout.setExpanded(true, true);
        return this;
    }

    private void reset(String title) {
        tvTitle.setText(title);
        webView.setVisibility(View.GONE);
    }

    @Override
    public void onDetached() {
        super.onDetached();
        positions.put(news.getId(), webView.getScrollY());
        imageView.setImageResource(android.R.color.transparent);
//        appBarLayout.setExpanded(false);
//        appBarLayout.setExpanded(true);
        appBarLayout.setExpanded(false);
    }

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        getManager().getSwitcher().back(false);
//    }
}
