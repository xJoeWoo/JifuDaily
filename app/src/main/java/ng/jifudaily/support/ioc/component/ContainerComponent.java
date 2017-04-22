package ng.jifudaily.support.ioc.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import ng.jifudaily.support.container.ContainerManager;
import ng.jifudaily.support.ioc.module.AppModule;
import ng.jifudaily.support.ioc.module.ContainerModule;
import ng.jifudaily.support.ioc.module.ServiceModule;
import ng.jifudaily.view.base.ContainerActivity;

/**
 * Created by Ng on 2017/4/22.
 */
@Component(modules = {ContainerModule.class})
@Singleton
public interface ContainerComponent {
    void injectContainerManager(ContainerManager manager);
//    void injectContainerActivity(ContainerActivity act);
}
