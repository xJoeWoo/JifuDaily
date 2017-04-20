package ng.jifudaily.support.ioc.component;

import javax.inject.Singleton;

import dagger.Component;
import ng.jifudaily.support.ioc.module.AppModule;
import ng.jifudaily.support.ioc.module.ImplementModule;
import ng.jifudaily.support.ioc.module.InterfaceModule;
import ng.jifudaily.view.base.BaseActivity;

/**
 * Created by Ng on 2017/4/20.
 */


@Singleton
@Component(modules = {InterfaceModule.class, ImplementModule.class, AppModule.class})
public interface ServiceComponent {
    void injectTo(BaseActivity activity);
}
