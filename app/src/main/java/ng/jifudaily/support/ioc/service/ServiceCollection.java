package ng.jifudaily.support.ioc.service;

import ng.jifudaily.support.ioc.log.LogService;

/**
 * Created by Ng on 2017/4/20.
 */

public interface ServiceCollection {
    NetService net();

    DailyService daily();

    LogService log();
}
