package ng.jifudaily.support.net.resource;

import io.reactivex.Flowable;
import ng.jifudaily.support.net.entity.NewsContentEntity;
import ng.jifudaily.support.net.entity.LatestNewsEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ng on 2017/4/20.
 */

public interface DailyNewsResource {
    @GET("latest")
    Flowable<LatestNewsEntity> latestNews();

    @GET("{id}")
    Flowable<NewsContentEntity> newsContent(@Path("id") int id);
}
