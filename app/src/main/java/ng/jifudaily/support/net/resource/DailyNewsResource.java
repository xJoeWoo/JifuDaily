package ng.jifudaily.support.net.resource;

import io.reactivex.Flowable;
import ng.jifudaily.support.net.bean.NewsContentBean;
import ng.jifudaily.support.net.bean.LatestNewsBean;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ng on 2017/4/20.
 */

public interface DailyNewsResource {
    @GET("latest")
    Flowable<LatestNewsBean> latestNews();

    @GET("{id}")
    Flowable<NewsContentBean> newsContent(@Path("id") int id);
}
