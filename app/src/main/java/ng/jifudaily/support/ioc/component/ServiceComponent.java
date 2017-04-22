package ng.jifudaily.support.ioc.component;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;
import ng.jifudaily.support.ioc.module.AppModule;
import ng.jifudaily.support.ioc.module.ServiceModule;

/**
 * Created by Ng on 2017/4/20.
 */


@Component(modules = {ServiceModule.class})
@Singleton
public interface ServiceComponent {
    void inject(Activity activity);
}
