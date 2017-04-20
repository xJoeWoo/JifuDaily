package ng.jifudaily.support.ioc.log;

import android.util.Log;

/**
 * Created by Ng on 2017/4/20.
 */

public class LogServiceImpl implements LogService {
    @Override
    public void error(String msg, String tag) {
        Log.e(tag, msg);
    }

    @Override
    public void error(String msg) {
        error(msg, "123");
    }
}
