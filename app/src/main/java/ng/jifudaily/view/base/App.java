package ng.jifudaily.view.base;

import android.app.Application;

import ng.jifudaily.support.ioc.component.ActivityComponent;
import ng.jifudaily.support.ioc.component.ContainerComponent;
import ng.jifudaily.support.ioc.component.DaggerActivityComponent;
import ng.jifudaily.support.ioc.component.DaggerContainerComponent;
import ng.jifudaily.support.ioc.module.AppModule;

/**
 * Created by Ng on 2017/4/20.
 */

public class App extends Application {

    //    private ServiceComponent serviceComponent;
    private ActivityComponent activityComponent;
    private ContainerComponent containerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        serviceComponent = DaggerServiceComponent
//                .builder()
//                .serviceModule(new ServiceModule())
//                .build();

        activityComponent = DaggerActivityComponent.builder().appModule(new AppModule(this)).build();
        containerComponent = DaggerContainerComponent.create();
    }

//    public ServiceComponent getServiceComponent() {
//        return serviceComponent;
//    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public ContainerComponent getContainerComponent() {
        return containerComponent;
    }
}
