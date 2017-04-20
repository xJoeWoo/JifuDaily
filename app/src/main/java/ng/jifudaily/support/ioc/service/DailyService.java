package ng.jifudaily.support.ioc.service;

import io.reactivex.Flowable;
import ng.jifudaily.support.net.bean.LatestNewsBean;
import ng.jifudaily.support.net.bean.NewsContentBean;

/**
 * Created by Ng on 2017/4/20.
 */

public interface DailyService {
    Flowable<LatestNewsBean> getLatestNews();

    Flowable<NewsContentBean> getNewsContent(int id);
}
