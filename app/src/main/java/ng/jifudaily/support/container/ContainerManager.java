package ng.jifudaily.support.container;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;
import ng.jifudaily.support.util.Action;
import ng.jifudaily.view.base.App;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerManager {

    private Activity activity;
    private List<Container> containers = new ArrayList<>();

    @Inject
    Lazy<ContainerSwitcher> switcherLazy;


    public ContainerManager bind(Activity act) {
        this.activity = act;
        ((App) activity.getApplication()).getContainerComponent().injectContainerManager(this);
        return this;
    }

    public ContainerManager add(Container container) {
        containers.add(container);
        return this;
    }

    public ContainerManager remove(Container container) {
        containers.remove(container);
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
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getContainerId() == containerId) {
                containers.remove(i);
                flag = true;
            }
        }
        return flag;
    }

    public void clear() {
        containers.clear();
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * If id is used in more than one container, then return the first container matches provided id
     *
     * @param containerId
     * @return
     */
    @Nullable
    public Container getContainer(int containerId) {
        for (Container c : containers) {
            if (c.getContainerId() == containerId) {
                return c;
            }
        }
        return null;
    }

    private boolean switcherInited = false;

    public ContainerSwitcher getSwitcher() {
        if (!switcherInited) {
            switcherLazy.get().bind(this);
            switcherInited = true;
        }

        return switcherLazy.get();
    }

    public List<Container> getContainers() {
        return containers;
    }

    public int getContainersCount() {
        return containers.size();
    }

    public void callContainers(final Action<Container> callback) {
        containers.forEach(callback::onCall);
    }

}
