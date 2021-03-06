package ng.jifudaily.support.ioc.module;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.ioc.service.DailyService;
import ng.jifudaily.support.ioc.service.LogService;
import ng.jifudaily.support.ioc.service.NetService;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import ng.jifudaily.support.ioc.service.ToolService;
import retrofit2.Retrofit;

/**
 * Created by Ng on 2017/4/20.
 */
@Module(includes = {NetModule.class, ToolModule.class})
public class ServiceModule {

    @Provides
    @Singleton
    public NetService providesNetService(Lazy<Retrofit.Builder> retrofitBuilder, Lazy<Picasso> picasso) {
        return new NetService(retrofitBuilder, picasso);
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
    public ServiceCollection providesServiceCollection(Lazy<NetService> netService, Lazy<DailyService> dailyService, Lazy<LogService> logService, Lazy<ToolService> toolServiceLazy) {
        return new ServiceCollection(netService, dailyService, logService, toolServiceLazy);
    }

    @Provides
    @Singleton
    public NetConf providesNetConf() {
        return new NetConf();
    }


}
