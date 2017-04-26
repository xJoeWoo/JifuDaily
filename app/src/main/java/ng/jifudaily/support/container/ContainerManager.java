package ng.jifudaily.support.container;

import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

import dagger.Lazy;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import ng.jifudaily.support.util.Action;
import ng.jifudaily.view.base.App;
import ng.jifudaily.view.base.ContainerActivity;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerManager {

    private final Set<Container> containers = new HashSet<>();
    @Inject
    Lazy<ContainerSwitcher> switcherLazy;
    private ContainerActivity activity;
    private boolean switcherInited = false;

    public ContainerManager bind(ContainerActivity act) {
        this.activity = act;
        ((App) activity.getApplication()).getContainerComponent().injectContainerManager(this);
        return this;
    }

    public ContainerManager add(Container container) {
        getContainers().add(container.manager(this));
        return this;
    }

    public ContainerManager remove(Container container) {
        getContainers().remove(container);
        return this;
    }

    /**
     * just a simple way add container then call <tt>Activity#setContent</tt>
     *
     * @param container
     * @return
     */
    public ContainerManager show(Container container) {
        add(container);
        getSwitcher().to(container);
        return this;
    }

    /**
     * Remove all containers that using provided id
     *
     * @param containerId
     * @return <tt>true</tt> if remove one or more container
     */
    public boolean remove(int containerId) {
        boolean flag = false;
        for (Iterator<Container> i = getContainers().iterator(); i.hasNext(); ) {
            if (i.next().getContainerId() == containerId) {
                i.remove();
                flag = true;
            }
        }
        return flag;
    }

    public void clear() {
        getContainers().clear();
    }

    public ContainerActivity getActivity() {
        return activity;
    }

    public ServiceCollection getService() {
        return activity.getServices();
    }

    /**
     * If id is used in more than one container, then return the first container matches provided id
     *
     * @param containerId
     * @return
     */
    @Nullable
    public <T extends Container<T>> T getContainer(int containerId, Class<T> clz) {
        Set<Container> cs = getContainers();
        for (Container c : cs) {
            if (c.getContainerId() == containerId) {
                return clz.cast(c);
            }
        }
        return null;
    }

    public ContainerSwitcher getSwitcher() {
        if (!switcherInited) {
            switcherLazy.get().manager(this);
            switcherInited = true;
        }

        return switcherLazy.get();
    }

    public Set<Container> getContainers() {
        return containers;
    }

    public int getContainersCount() {
        return getContainers().size();
    }

    public void callContainers(final Action<Container> callback) {
//        containers.forEach(x->callback.onCall(x));
        for (Container c :
                containers) {
            callback.onCall(c);
        }
    }

    public boolean onBackPressed() {
        for (Container c :
                containers) {
            if (c.isAttached() && c.onBackPressed()) {
                return true;
            }
        }
        return getSwitcher().back();
    }

}
