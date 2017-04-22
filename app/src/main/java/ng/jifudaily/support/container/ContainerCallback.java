package ng.jifudaily.support.container;

import android.os.Message;

/**
 * Created by Ng on 2017/4/22.
 */

public class ContainerCallback {

    public static final int DEFAULT_WHAT = -1;

    private int what = DEFAULT_WHAT;
    private Object obj;

    public ContainerCallback() {
    }

    public ContainerCallback what(int what) {
        this.what = what;
        return this;
    }

    public ContainerCallback obj(Object obj) {
        this.obj = obj;
        return this;
    }
}
