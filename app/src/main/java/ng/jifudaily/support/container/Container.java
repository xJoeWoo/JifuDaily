package ng.jifudaily.support.container;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import ng.jifudaily.support.util.Disposable;
import ng.jifudaily.support.util.LifeCycle;
import ng.jifudaily.view.base.ContainerActivity;


/**
 * Created by Ng on 2017/4/21.
 */

public class Container<T extends Container<T>> implements LifeCycle, Disposable {

    public static final int DEFAULT_ID = -1;

    private View rootView;
    private int containerId = DEFAULT_ID;
    private boolean visibleToUser = true;
    private PublishProcessor<ContainerCallback> processor;
    private io.reactivex.disposables.Disposable subDisposable;
    private ContainerManager manager;
    private boolean shouldDispose = true;

//    protected Container(ContainerManager manager) {
//        this.manager = manager;
//    }
//
//
//    public Container() {
//    }

//    public static <T extends Container<T>> T createInstance(Class<T> clz) {
//        try {
//            return clz.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private T getThis() {
        return (T) this;
    }

    public T manager(ContainerManager manager) {
        this.manager = manager;
        return getThis();
    }

    public T bind(@LayoutRes int layoutId, Context context) {
        bind(LayoutInflater.from(context).inflate(layoutId, null));
        return getThis();
    }

    public T bind(View rootView) {
        this.rootView = rootView;
        if (rootView != null) {
            onBind(this.rootView);
        }
        return getThis();
    }

    public T id(int id) {
        containerId = id;
        return getThis();
    }

    public T subscribe(DisposableSubscriber<ContainerCallback> subscriber) {
        subDisposable = getProcessor().subscribeWith(subscriber);
        return getThis();
    }

    public T shouldDispose(boolean dispose) {
        shouldDispose = dispose;
        return getThis();
    }

    public Flowable<ContainerCallback> getFlowable() {
        return getProcessor();
    }

    private PublishProcessor<ContainerCallback> getProcessor() {
        if (processor == null) {
            processor = PublishProcessor.create();
        }
        return processor;
    }

    protected ContainerManager getManager() {
        return manager;
    }

    protected ContainerActivity getActivity() {
        return manager.getActivity();
    }

    protected Picasso getPicasso() {
        return getActivity().getServices().net().picasso();
    }

    public View getContainingView() {
        return rootView;
    }

    public int getContainerId() {
        return containerId;
    }


    /**
     * View call <tt>ButterKnife#manager</tt> to inject views
     *
     * @return
     */
    protected void onBind(View v) {
        ButterKnife.bind(this, v);
    }

    protected void publish(int what) {
        publish(what, null);
    }

    protected void publish(int what, Object obj) {
        getProcessor().onNext(new ContainerCallback(what, obj));
    }

    public void onVisibleToUser(boolean isVisible) {
        visibleToUser = isVisible;
    }

    public void onBeforeAttach() {
    }

    public void onAttached() {
    }

    public void onDetached() {
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    protected final <E extends View> E view(@IdRes int id) {
        try {
            return (E) rootView.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("UiBlock", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    @Override
    public void dispose() {
        if (!shouldDispose) {
            return;
        }
        if (subDisposable != null) {
            subDisposable.dispose();
        }
        if (processor != null) {
            processor.onComplete();
            processor = null;
        }
        rootView.setVisibility(View.GONE);
    }


//    public static class Builder {
//
//        private int containerId = DEFAULT_ID;
//        private View view;
//
//        private ContainerManager manager;
//
//        public Builder() {
//        }
//
//        public Builder id(int id) {
//            containerId = id;
//            return getThis();
//        }
//
//        public Builder bind(View view) {
//            this.view = view;
//            return getThis();
//        }
//
//        public Builder manager(ContainerManager manager) {
//            this.manager = manager;
//            return getThis();
//        }
//
//        public Builder bind(@LayoutRes int layoutId, Context context) {
//            this.view = LayoutInflater.from(context).inflate(layoutId, null);
//            return getThis();
//        }
//
//
//        public Builder reset() {
//            containerId = DEFAULT_ID;
//            view = null;
//            return getThis();
//        }
//
//        public <T extends Container> T build(Class<T> clz) {
//            if (view == null) {
//                throw new IllegalArgumentException("Need View to create a Container");
//            }
//            if (manager == null) {
//                throw new IllegalArgumentException("Need ContainerManager to create Container");
//            }
//            try {
//                Constructor<T> constructor = clz.getConstructor(ContainerManager.class);
//                constructor.setAccessible(true);
//                return (T) constructor.newInstance(manager).id(containerId).bind(view);
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
}
