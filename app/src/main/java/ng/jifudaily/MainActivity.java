package ng.jifudaily;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.container.ContainerCallback;
import ng.jifudaily.support.net.entity.LatestNewsEntity;
import ng.jifudaily.support.net.entity.StoryEntity;
import ng.jifudaily.support.util.SubscriberAdapter;
import ng.jifudaily.view.base.ContainerActivity;
import ng.jifudaily.view.container.LatestNewsContainer;
import ng.jifudaily.view.container.NewsContentContainer;

public class MainActivity extends ContainerActivity {

    public static final int LATEST_NEWS_CONTAINER = 1;
    public static final int NEWS_CONTENT_CONTAINER = 2;

    private LatestNewsContainer latestNewsContainer;
    private NewsContentContainer newsContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.container_latest_news);

        List<StoryEntity> holders = new ArrayList<>();
        StoryEntity holder = new StoryEntity();
        holders.add(holder);
        holders.add(holder);
        holders.add(holder);
        holders.add(holder);
        holders.add(holder);
        holders.add(holder);
        holders.add(holder);

        latestNewsContainer = createContainer(LatestNewsContainer.class)
                .id(LATEST_NEWS_CONTAINER)
                .bind(R.layout.container_latest_news, this)
                .latestNews(holders);

        getContainerManager().show(latestNewsContainer);

        getServices().daily().getLatestNews().subscribe(new SubscriberAdapter<LatestNewsEntity>() {
            @Override
            public void onNext(LatestNewsEntity entity) {
                latestNewsContainer.latestNews(entity.getStories());
            }
        });

        latestNewsContainer.getFlowable()
                .filter(c -> c.getWhat() == LatestNewsContainer.NEWS_CLICK)
                .map(c -> (StoryEntity) c.getObj())
                .subscribe(story -> getContainerManager().getSwitcher().switchTo(getNewsContentContainer().load(story)));
//                .flatMap(news -> getServices().daily().getNewsContent(news.getId()))
//                .subscribe(story -> getContainerManager().getSwitcher().switchTo(newsContentContainer.news(story)), error -> getServices().log().error(error.getMessage()));

//
//        getServices().daily().getLatestNews().subscribe(x -> {
//            x.getStories().get(0).getId();
//        });

//        Observable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(x -> {
//            getContainerManager().getSwitcher().switchFrom(c1).to(c2);
//        });

    }

    private NewsContentContainer getNewsContentContainer() {
        if (newsContentContainer == null) {
            newsContentContainer = createContainer(NewsContentContainer.class)
                    .id(NEWS_CONTENT_CONTAINER)
                    .bind(R.layout.container_news_content, this);
        }
        return newsContentContainer;
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent manager in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}


