package ng.jifudaily;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ng.jifudaily.support.net.entity.StoryEntity;
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


        latestNewsContainer = createContainer(LatestNewsContainer.class)
                .id(LATEST_NEWS_CONTAINER)
                .bind(R.layout.container_latest_news);

        newsContentContainer = createContainer(NewsContentContainer.class)
                .id(NEWS_CONTENT_CONTAINER)
                .bind(R.layout.container_news_content);

        getContainerManager().show(latestNewsContainer.loadLatestNews());

        latestNewsContainer.getFlowable()
                .filter(c -> c.getWhat() == LatestNewsContainer.NEWS_CLICK)
                .map(c -> (StoryEntity) c.getObj())
                .subscribe(story -> getContainerManager().getSwitcher().to(newsContentContainer.init(story)));
//                .flatMap(processNewsContent -> getServices().daily().getNewsContent(processNewsContent.getId()))
//                .subscribe(story -> getContainerManager().getSwitcher().switchTo(newsContentContainer.processNewsContent(story)), error -> getServices().log().error(error.getMessage()));


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


