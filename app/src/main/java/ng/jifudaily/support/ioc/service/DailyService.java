package ng.jifudaily.support.ioc.service;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.net.entity.LatestNewsEntity;
import ng.jifudaily.support.net.entity.NewsContentEntity;
import ng.jifudaily.support.net.resource.DailyNewsResource;

/**
 * Created by Ng on 2017/4/20.
 */

public class DailyService {

    private NetService netService;
    private NetConf netConf;

    @Inject
    public DailyService(NetService netService, NetConf netConf) {
        this.netService = netService;
        this.netConf = netConf;
    }

    public Flowable<LatestNewsEntity> getLatestNews() {
        return init(newsService().latestNews());
    }

    public Flowable<NewsContentEntity> getNewsContent(int id) {

        return init(newsService().newsContent(id)).map(news -> {
            StringBuilder sb = new StringBuilder("<!DOCTYPE html> <html> <head>\n");
            for (String js :
                    news.getJsUrls()) {
                sb.append("<script src=\"").append(js).append("\"></script>\n");
            }
            for (String css :
                    news.getCssUrls()) {
                sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(css).append("\">");
            }
            sb.append("</head>");
            sb.append("<body>").append(news.getContentBody()).append("</body>").append("</html>");

            news.setCompleteHTML(sb.toString());
            return news;
        });

    }

    private <T> T service(String baseUrl, Class<T> service) {
        return netService.create(baseUrl, service);
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
