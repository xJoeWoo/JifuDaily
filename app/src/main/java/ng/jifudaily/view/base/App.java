package ng.jifudaily.view.base;

import android.app.Application;

import ng.jifudaily.support.ioc.component.DaggerServiceComponent;
import ng.jifudaily.support.ioc.component.ServiceComponent;

/**
 * Created by Ng on 2017/4/20.
 */

public class App extends Application {

    private ServiceComponent serviceComponent ;


    @Override
    public void onCreate() {
        super.onCreate();

        serviceComponent = DaggerServiceComponent.create();

    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }
}
