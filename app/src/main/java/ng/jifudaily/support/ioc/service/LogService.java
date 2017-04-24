package ng.jifudaily.support.ioc.service;

import android.util.Log;

/**
 * Created by Ng on 2017/4/20.
 */

public class LogService {
    public void error(String msg, String tag) {
        Log.e(tag, msg);
    }

    public void error(String msg) {
        error(msg, "Jifu");
    }
}
