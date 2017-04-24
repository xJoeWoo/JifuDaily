package ng.jifudaily.support.ioc.module;

import android.app.Application;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.ioc.service.AnimService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ng on 2017/4/24.
 */
@Module(includes = {AppModule.class})

public class NetModule {
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
    public Picasso providesPicasso(OkHttpClient client, Application app) {
        return new Picasso.Builder(app).build();
    }

}
