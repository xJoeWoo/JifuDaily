package ng.jifudaily.support.ioc.component;

import javax.inject.Singleton;

import dagger.Component;
import ng.jifudaily.support.ioc.module.ContainerModule;
import ng.jifudaily.support.ioc.module.ServiceModule;
import ng.jifudaily.view.base.ContainerActivity;
import ng.jifudaily.view.base.BaseActivity;

/**
 * Created by Ng on 2017/4/21.
 */
@Component(modules = {ServiceModule.class, ContainerModule.class})
@Singleton
public interface ActivityComponent {
    void injectBaseActivity(BaseActivity activity);

    void injectContainerActivity(ContainerActivity act);

}
