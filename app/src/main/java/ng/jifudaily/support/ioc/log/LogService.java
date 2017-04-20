package ng.jifudaily.support.ioc.log;

/**
 * Created by Ng on 2017/4/20.
 */

public interface LogService {
    void error(String msg, String tag);

    void error(String msg);
}
