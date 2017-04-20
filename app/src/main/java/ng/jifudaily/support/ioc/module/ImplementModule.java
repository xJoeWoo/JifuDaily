package ng.jifudaily.support.ioc.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.ioc.conf.NetConfImpl;
import ng.jifudaily.support.ioc.log.LogService;
import ng.jifudaily.support.ioc.log.LogServiceImpl;
import ng.jifudaily.support.ioc.service.DailyService;
import ng.jifudaily.support.ioc.service.DailyServiceImpl;
import ng.jifudaily.support.ioc.service.NetService;
import ng.jifudaily.support.ioc.service.NetServiceImpl;
import ng.jifudaily.support.ioc.service.ServiceCollectionImpl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ng on 2017/4/20.
 */
@Module
public class ImplementModule {
    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(NetConf conf) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.connectTimeout(conf.getNetTimeout(), TimeUnit.MILLISECONDS);
        return httpClientBuilder.build();
    }

//    @Provides
//    @Singleton
//    public Gson providesGson() {
//        return new GsonBuilder().create();
//    }
//

    @Provides
    @Singleton
    public Retrofit.Builder providesRetrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    @Provides
    @Singleton
    public NetServiceImpl providesNetServiceImpl(Retrofit.Builder retrofitBuilder) {
        return new NetServiceImpl(retrofitBuilder);
    }

    @Provides
    @Singleton
    public LogServiceImpl providesLogServiceImpl() {
        return new LogServiceImpl();
    }

    @Provides
    @Singleton
    public DailyServiceImpl providesDailyServiceImpl(NetService netService, NetConfImpl netConf) {
        return new DailyServiceImpl(netService, netConf);
    }

    @Provides
    @Singleton
    public ServiceCollectionImpl providesServiceCollectionImpl(Lazy<NetService> netService, Lazy<DailyService> dailyService, Lazy<LogService> logService) {
        return new ServiceCollectionImpl(netService, dailyService, logService);
    }

    @Provides
    @Singleton
    public NetConfImpl providesNetConfImpl() {
        return new NetConfImpl();
    }


}
