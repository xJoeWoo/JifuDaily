package ng.jifudaily.support.ioc.service;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Retrofit;


/**
 * Created by Ng on 2017/4/20.
 */

public class NetService {


    private HashMap<String, Retrofit> retrofitMaps = new HashMap<>();

    private Lazy<Retrofit.Builder> retrofitBuilder;
    private Lazy<Picasso> picasso;

    @Inject
    public NetService(Lazy<Retrofit.Builder> retrofitBuilder, Lazy<Picasso> picasso) {
        this.retrofitBuilder = retrofitBuilder;
        this.picasso = picasso;
    }

    public <T> T create(String baseUrl, Class<T> service) {
        return getRetrofit(baseUrl).create(service);
    }

    private Retrofit getRetrofit(String baseUrl) {

        if (retrofitMaps.containsKey(baseUrl)) {
            return retrofitMaps.get(baseUrl);
        }

        Retrofit r = retrofitBuilder.get()
                .baseUrl(baseUrl)
                .build();

        retrofitMaps.put(baseUrl, r);

        return r;

    }

    public Picasso picasso() {
        return picasso.get();
    }


}
