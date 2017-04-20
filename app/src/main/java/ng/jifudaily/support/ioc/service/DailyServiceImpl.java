package ng.jifudaily.support.ioc.service;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ng.jifudaily.support.ioc.conf.NetConfImpl;
import ng.jifudaily.support.net.bean.NewsContentBean;
import ng.jifudaily.support.net.bean.LatestNewsBean;
import ng.jifudaily.support.net.resource.DailyNewsResource;

/**
 * Created by Ng on 2017/4/20.
 */

public class DailyServiceImpl implements DailyService {

    private NetService netService;
    private NetConfImpl netConf;

    @Inject
    public DailyServiceImpl(NetService netService, NetConfImpl netConf) {
        this.netService = netService;
        this.netConf = netConf;
    }

    @Override
    public Flowable<LatestNewsBean> getLatestNews() {
        return init(newsService().latestNews());
    }

    @Override
    public Flowable<NewsContentBean> getNewsContent(int id) {
        return init(newsService().newsContent(id));
    }

    private <T> T service(String baseUrl, Class<T> service) {
        return netService.Create(baseUrl, service);
    }

    private DailyNewsResource newsService() {
        return service(CombineUrl(netConf.getNewsAction()), DailyNewsResource.class);
    }

    private <T> Flowable<T> init(@NonNull Flowable<T> source) {
        return source
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String CombineUrl(String action) {
        return netConf.getBaseUrl() + "/" + action + "/";
    }

}
