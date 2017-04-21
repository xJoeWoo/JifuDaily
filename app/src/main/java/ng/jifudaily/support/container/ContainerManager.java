package ng.jifudaily.support.container;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerManager {


    private final Context context;
    private List<Container> containers = new ArrayList<Container>();

    public ContainerManager(Context context) {
        this.context = context;
    }

    public ContainerManager add(Container container) {
        containers.add(container);
        return this;
    }

    @Nullable
    public Container get(int containerId) {
        for (Container c : containers) {
            if (c.getContainerId() == containerId) {
                return c;
            }
        }
        return null;
    }

    public List<Container> getContainers() {
        return containers;
    }
}
