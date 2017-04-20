package ng.jifudaily.support.ioc.module;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ng.jifudaily.support.ioc.conf.NetConf;
import ng.jifudaily.support.ioc.conf.NetConfImpl;
import ng.jifudaily.support.ioc.log.LogService;
import ng.jifudaily.support.ioc.log.LogServiceImpl;
import ng.jifudaily.support.ioc.service.DailyService;
import ng.jifudaily.support.ioc.service.DailyServiceImpl;
import ng.jifudaily.support.ioc.service.NetService;
import ng.jifudaily.support.ioc.service.NetServiceImpl;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import ng.jifudaily.support.ioc.service.ServiceCollectionImpl;

/**
 * Created by Ng on 2017/4/20.
 */
@Module
public abstract class InterfaceModule {

    @Binds
    @Singleton
    public abstract LogService bindLogService(LogServiceImpl logService);

    @Binds
    @Singleton
    public abstract DailyService bindDailyService(DailyServiceImpl dailyServiceImpl);

    @Binds
    @Singleton
    public abstract NetService bindNetService(NetServiceImpl netServiceImpl);

    @Binds
    @Singleton
    public abstract ServiceCollection bindServices(ServiceCollectionImpl servicesImpl);

    @Binds
    @Singleton
    public abstract NetConf bindNetConf(NetConfImpl netConfImpl);

}
