package ng.jifudaily.support.ioc.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.container.ContainerCallback;
import ng.jifudaily.support.container.ContainerManager;
import ng.jifudaily.support.container.ContainerSwitcher;

/**
 * Created by Ng on 2017/4/21.
 */
@Module
public class ContainerModule {

    @Provides
    public ContainerManager providesContainerManager() {
        return new ContainerManager();
    }

    @Provides
    public Container providesContainer() {
        return new Container();
    }

    @Provides
    public Container.Builder providesContainerBuilder() {
        return new Container.Builder();
    }

    @Provides
    public ContainerSwitcher providesContainerSwitcher() {
        return new ContainerSwitcher();
    }

    @Provides
    public ContainerCallback providesContainerCallback() {
        return new ContainerCallback();
    }
}
