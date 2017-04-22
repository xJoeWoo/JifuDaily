package ng.jifudaily.support.util;

/**
 * Created by Ng on 2017/4/22.
 */

public interface Action<T> {
    void onCall(T obj);
}
