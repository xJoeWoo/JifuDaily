package ng.jifudaily.support.ioc.service;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Retrofit;


/**
 * Created by Ng on 2017/4/20.
 */

public class NetServiceImpl implements NetService {


    private HashMap<String, Retrofit> retrofitMaps = new HashMap<>();

    private Retrofit.Builder retrofitBuilder;

    @Inject
    public NetServiceImpl(Retrofit.Builder retrofitBuilder) {
        this.retrofitBuilder = retrofitBuilder;
    }

    @Override
    public <T> T Create(String baseUrl, Class<T> service) {
        return getRetrofit(baseUrl).create(service);
    }

    private Retrofit getRetrofit(String baseUrl) {

        if (retrofitMaps.containsKey(baseUrl)) {
            return retrofitMaps.get(baseUrl);
        }

        Retrofit r = retrofitBuilder
                .baseUrl(baseUrl)
                .build();

        retrofitMaps.put(baseUrl, r);

        return r;

    }


}
