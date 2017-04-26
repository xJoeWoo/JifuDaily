package ng.jifudaily.support.util;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Ng on 2017/4/24.
 */

public abstract class ObservableRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, LI> extends RecyclerView.Adapter<VH> {

    private List<LI> dataList = new ArrayList<>();

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

    public List<LI> getData() {
        return dataList;
    }

    public ObservableRecyclerViewAdapter<VH, LI> updateRange(int position, Collection<? extends LI> collection) {
        int originSize = getData().size();
        if (position > originSize) {
            throw new UnsupportedOperationException("Update start position is bigger than current data collection size");
        }
        int i = position;
        for (LI newData : collection) {
            if (i < originSize) {
                dataList.set(i, newData);
                notifyItemChanged(i);
            } else {
                dataList.add(i, newData);
                notifyItemInserted(i);
            }
            i++;
        }
        return this;
    }

    public ObservableRecyclerViewAdapter<VH, LI> changeRange(int position, int count) {
        notifyItemRangeChanged(position, count);
        return this;
    }

    public ObservableRecyclerViewAdapter<VH, LI> clear() {
        replace(null);
        return this;
    }

    public ObservableRecyclerViewAdapter<VH, LI> replace(Collection<? extends LI> collection) {
        int originSize = getData().size();
        getData().clear();
        if (collection == null) {
            notifyItemRangeRemoved(0, originSize);
            return this;
        }
        int newSize = collection.size();
        int dSize = newSize - originSize;
        getData().addAll(collection);
        notifyItemRangeChanged(0, originSize);
        if (dSize > 0) {
            notifyItemRangeInserted(originSize, newSize);
        } else if (dSize < 0) {
            notifyItemRangeRemoved(originSize, newSize);
        }
        return this;
    }


    public ObservableRecyclerViewAdapter<VH, LI> changeRange(int position, Collection<? extends LI> collection) {
        int i = position;
        for (LI newData : collection) {
            getData().set(i++, newData);
        }
        notifyItemRangeChanged(position, collection.size());
        return this;
    }

    public ObservableRecyclerViewAdapter<VH, LI> addRange(int position, Collection<? extends LI> collection) {
        getData().addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
        return this;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
