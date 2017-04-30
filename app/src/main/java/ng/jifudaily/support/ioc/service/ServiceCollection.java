package ng.jifudaily.support.ioc.service;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Ng on 2017/4/20.
 */

public class ServiceCollection {

    private Lazy<NetService> netService;
    private Lazy<DailyService> dailyService;
    private Lazy<LogService> logService;
    private Lazy<ToolService> utilService;

    @Inject
    public ServiceCollection(Lazy<NetService> netService, Lazy<DailyService> dailyService, Lazy<LogService> logService, Lazy<ToolService> toolServiceLazy) {
        this.netService = netService;
        this.dailyService = dailyService;
        this.logService = logService;
        this.utilService = toolServiceLazy;
    }

    public NetService net() {
        return netService.get();
    }

    public DailyService daily() {
        return dailyService.get();
    }

    public LogService log() {
        return logService.get();
    }

    public ToolService tool() {
        return utilService.get();
    }
}


