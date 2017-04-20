package ng.jifudaily.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import ng.jifudaily.support.ioc.service.ServiceCollection;

/**
 * Created by Ng on 2017/4/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    ServiceCollection services;


    protected ServiceCollection getServices() {
        return services;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.e("asdfasdf", "BaseCreate");

        ((App) getApplication()).getServiceComponent().injectTo(this);

//        Retrofit.Builder n = new Retrofit.Builder();
//        OkHttpClient.Builder h = new OkHttpClient.Builder();
//
//        h.connectTimeout(1000, TimeUnit.MILLISECONDS);
//
//        n.baseUrl("http://news-at.zhihu.com/api/4/news/")
//                .                client(h.build())
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
//        Retrofit r = n.build();
//        r.create(DailyNewsResource.class).latestNews()
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(x -> {
//                    Log.e("asdfadsf", x.getStories().get(0).getTitle());
//                    getServices().log().error("asdfadsf", x.getStories().get(0).getTitle());
//                }, e -> {
//                    Log.e("asdfasdf", e.getMessage());
//                });


//        DaggerServiceComponent.builder()
//                .build();



    }
}
