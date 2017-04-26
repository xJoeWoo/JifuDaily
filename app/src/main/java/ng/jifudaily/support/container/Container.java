package ng.jifudaily.support.container;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import ng.jifudaily.support.util.LifeCycle;


/**
 * Created by Ng on 2017/4/21.
 */

public abstract class Container<T extends Container<T>> implements LifeCycle, Disposable {

    public static final int DEFAULT_ID = -1;

    private View rootView;
    private int containerId = DEFAULT_ID;
    private boolean visibleToUser = true;
    private PublishProcessor<ContainerCallback> processor;
    private ContainerManager manager;
    private boolean shouldDispose = true;
    private boolean isAttached = false;
    private int layoutId = -1;
    private Context context;
    private boolean requestNewView = false;
    private Unbinder unbinder;

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
private boolean disposed = false;

    private T getThis() {
        return (T) this;
    }

    public T manager(ContainerManager manager) {
        this.manager = manager;
        return getThis();
    }


//    public T bind(View rootView) {
//        this.rootView = rootView;
//        if (rootView != null) {
//            onViewCreated(this.rootView);
//        }
//        return getThis();
//    }

    public T bind(@LayoutRes int layoutId, Context context) {
        this.layoutId = layoutId;
        this.context = context;
        onBind();
        return getThis();
    }

    public T id(int id) {
        containerId = id;
        return getThis();
    }

    protected T setShouldDispose(boolean dispose) {
        shouldDispose = dispose;
        return getThis();
    }

    protected boolean shouldDispose() {
        return shouldDispose;
    }

    public boolean isRequestNewView() {
        return requestNewView;
    }

    protected T setRequestNewView(boolean requestNewView) {
        this.requestNewView = requestNewView;
        return getThis();
    }

    public Transition getEnterTransition() {
        return null;
    }

    public Transition getExitTransition() {
        return null;
    }

    public Transition getReenterTransition() {
        return null;
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

    public boolean isAttached() {
        return isAttached;
    }

    public Container setAttached(boolean attached) {
        isAttached = attached;
        return this;
    }

    protected ServiceCollection getServices() {
        return getManager().getActivity().getServices();
    }

    public View createContainingView() {
        rootView = LayoutInflater.from(getContext()).inflate(getBindLayoutId(), null);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onViewDrawn(rootView);
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        onViewCreated(rootView);
        return rootView;
    }

    protected void onViewDrawn(View v) {
    }

    public View getContainingView() {
        return rootView == null ? createContainingView() : rootView;
    }

    public int getContainerId() {
        return containerId;
    }

    public int getBindLayoutId() {
        return layoutId;
    }

    protected Context getContext() {
        return context;
    }

    /**
     * View call <tt>ButterKnife#manager</tt> to inject views
     *
     * @return
     */
    protected void onViewCreated(View v) {
        unbinder = ButterKnife.bind(this, v);
    }

    protected void publish(int what) {
        publish(what, null);
    }

    protected void publish(int what, Object obj) {
        getProcessor().onNext(new ContainerCallback(what, obj));
    }

    protected void onBind() {
        createContainingView();
    }

    public void onVisibleToUser(boolean isVisible) {
        visibleToUser = isVisible;
    }

    public void onBeforeAttach() {
    }

    public void onAttached() {
    }

    public void onBeforeDetach() {
    }

    /**
     * Don't set view states for next attach, switching between will clear those settings
     */
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

    @Override
    public void dispose() {
        if (processor != null) {
            processor.onComplete();
            processor = null;
        }
        disposeView();
        disposed = true;
    }

    public void disposeView() {
        rootView.setVisibility(View.GONE);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
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
