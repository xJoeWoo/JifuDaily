package ng.jifudaily.support.util;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Ng on 2017/4/24.
 */

public abstract class ObservableRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private PublishSubject<Object> subject;

    public Observable<Object> getObservable() {
        return getSubject();
    }

    protected void publish(Object obj) {
        getSubject().onNext(obj);
    }

    private PublishSubject<Object> getSubject() {
        if (subject == null) {
            subject = PublishSubject.create();
        }
        return subject;
    }
}
