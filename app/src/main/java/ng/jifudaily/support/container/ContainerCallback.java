package ng.jifudaily.support.container;

import android.os.Message;

/**
 * Created by Ng on 2017/4/22.
 */

public class ContainerCallback {

    public static final int DEFAULT_WHAT = -1;

    private int what = DEFAULT_WHAT;
    private Object obj;

    public ContainerCallback(int what) {
        this.what = what;
    }

    public ContainerCallback(int what, Object obj) {
        this.what = what;
        this.obj = obj;
    }

    public int getWhat() {
        return what;
    }

    public Object getObj() {
        return obj;
    }
}
