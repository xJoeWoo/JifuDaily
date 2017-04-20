package ng.jifudaily.support.ioc.service;

import javax.inject.Inject;

import dagger.Lazy;
import ng.jifudaily.support.ioc.log.LogService;

/**
 * Created by Ng on 2017/4/20.
 */

public class ServiceCollectionImpl implements ServiceCollection {

    private Lazy<NetService> netService;
    private Lazy<DailyService> dailyService;
    private Lazy<LogService> logService;

    @Inject
    public ServiceCollectionImpl(Lazy<NetService> netService, Lazy<DailyService> dailyService, Lazy<LogService> logService) {
        this.netService = netService;
        this.dailyService = dailyService;
        this.logService = logService;
    }

    @Override
    public NetService net() {
        return netService.get();
    }

    @Override
    public DailyService daily() {
        return dailyService.get();
    }

    @Override
    public LogService log() {
        return logService.get();
    }
}
