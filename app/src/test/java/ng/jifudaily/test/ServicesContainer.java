package ng.jifudaily.test;

import javax.inject.Inject;

import ng.jifudaily.support.ioc.service.ServiceCollection;

/**
 * Created by Ng on 2017/4/20.
 */

public class ServicesContainer {

    @Inject
    ServiceCollection services;

    public ServiceCollection getServices() {
        return services;
    }

}
