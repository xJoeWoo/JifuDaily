package ng.jifudaily;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import ng.jifudaily.support.container.Container;
import ng.jifudaily.view.base.ContainerActivity;

public class MainActivity extends ContainerActivity {

    public static final int LATEST_NEWS_CONTAINER = 1;
    public static final int NEWS_CONTENT_CONTAINER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_latest_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        getContainerManager().add(getContainerBuilder()
                .bind(findViewById(R.id.main_tv))
                .id(LATEST_NEWS_CONTAINER)
                .bind(R.layout.container_latest_news, this)
                .build(Container.class));

        getServices().daily().getLatestNews().subscribe(x -> {
            x.getStories().get(0).getId();
        });

//        Observable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(x -> {
//            getContainerManager().getSwitcher().switchFrom(c1).to(c2);
//        });

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
//        // as you specify a parent bind in AndroidManifest.xml.
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


