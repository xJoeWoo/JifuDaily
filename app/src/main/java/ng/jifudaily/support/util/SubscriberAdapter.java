package ng.jifudaily.support.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Ng on 2017/4/20.
 */

public abstract class SubscriberAdapter<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE - 1);
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
