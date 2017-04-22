package ng.jifudaily.support.ioc.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.ioc.log.LogService;
import ng.jifudaily.support.ioc.service.DailyService;
import ng.jifudaily.support.ioc.service.NetService;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ng on 2017/4/20.
 */
@Module
public class ServiceModule {
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
    public NetService providesNetService(Retrofit.Builder retrofitBuilder) {
        return new NetService(retrofitBuilder);
    }

    @Provides
    @Singleton
    public LogService providesLogService() {
        return new LogService();
    }

    @Provides
    @Singleton
    public DailyService providesDailyService(NetService netService, NetConf netConf) {
        return new DailyService(netService, netConf);
    }

    @Provides
    @Singleton
    public ServiceCollection providesServiceCollection(Lazy<NetService> netService, Lazy<DailyService> dailyService, Lazy<LogService> logService) {
        return new ServiceCollection(netService, dailyService, logService);
    }

    @Provides
    @Singleton
    public NetConf providesNetConf() {
        return new NetConf();
    }


}
