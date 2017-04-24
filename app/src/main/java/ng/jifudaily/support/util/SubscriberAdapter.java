package ng.jifudaily.support.util;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Ng on 2017/4/20.
 */

public abstract class SubscriberAdapter<T> extends DisposableSubscriber<T> {

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable t) {
        Log.e("Subscriber", t.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
