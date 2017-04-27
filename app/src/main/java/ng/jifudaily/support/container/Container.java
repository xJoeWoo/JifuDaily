package ng.jifudaily.support.container;

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
import ng.jifudaily.view.base.ContainerActivity;


/**
 * Created by Ng on 2017/4/21.
 */

public abstract class Container<T extends Container<T>> implements LifeCycle, Disposable {

    public static final int DEFAULT_ID = -1;

    private View containingView;
    private int containerId = DEFAULT_ID;
    private boolean visibleToUser = true;
    private PublishProcessor<ContainerCallback> processor;
    private ContainerManager manager;
    private boolean shouldDispose = true;
    private boolean isAttached = false;
    private int layoutId = -1;
    private boolean requestNewView = false;
    private Unbinder unbinder;
    private boolean disposed = false;

    private T getThis() {
        return (T) this;
    }

    public T manager(ContainerManager manager) {
        this.manager = manager;
        return getThis();
    }


    public T bind(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
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

    protected void publish(int what) {
        publish(what, null);
    }

    protected void publish(int what, Object obj) {
        getProcessor().onNext(new ContainerCallback(what, obj));
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
        return getActivity().getServices();
    }

    protected ContainerActivity getActivity() {
        return getManager().getActivity();
    }

    public View createContainingView() {
        containingView = LayoutInflater.from(getManager().getActivity()).inflate(getBindLayoutId(), null, false);
        containingView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onViewDrawn(containingView);
                containingView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        onViewCreated(containingView);
        return containingView;
    }

    public View getContainingView() {
        return containingView == null ? createContainingView() : containingView;
    }

    public int getContainerId() {
        return containerId;
    }

    public int getBindLayoutId() {
        return layoutId;
    }


    protected void onViewDrawn(View v) {
    }

    /**
     * View call <tt>ButterKnife#manager</tt> to inject views
     */
    protected void onViewCreated(View v) {
        unbinder = ButterKnife.bind(this, v);
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
     * Don't set view states for next attach, switching between may clear those settings
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

    // Only this method is called when switch
    public void disposeView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}
