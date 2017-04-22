package ng.jifudaily.support.container;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ng.jifudaily.support.util.Disposable;
import ng.jifudaily.support.util.LifeCycle;


/**
 * Created by Ng on 2017/4/21.
 */

public class Container implements LifeCycle, Disposable {

    public static final int DEFAULT_ID = -1;

    private View rootView;

    private int containerId;

    private boolean visibleToUser = true;

    public Container() {
    }

    public Container(int containerId, View rootView) {
        this.containerId = containerId;
        bind(rootView);
    }

    public Container bind(View rootView) {
        this.rootView = rootView;
        if (rootView != null) {
            onBind(this.rootView);
        }
        return this;
    }

    public Container bind(@LayoutRes int layoutId, Context context) {
        bind(LayoutInflater.from(context).inflate(layoutId, null));
        return this;
    }


    public Container id(int id) {
        containerId = id;
        return this;
    }

    public View getContainingView() {
        return rootView;
    }

    public int getContainerId() {
        return containerId;
    }

    protected void onBind(View v) {
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
    public void onBackPressed() {

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
        rootView.setVisibility(View.GONE);
    }


    public static class Builder {

        private int containerId = DEFAULT_ID;
        private View view;

        public Builder() {
        }

        public Builder id(int id) {
            containerId = id;
            return this;
        }

        public Builder bind(View view) {
            this.view = view;
            return this;
        }

        public Builder bind(@LayoutRes int layoutId, Context context) {
            this.view = LayoutInflater.from(context).inflate(layoutId, null);
            return this;
        }

        public Builder reset() {
            containerId = DEFAULT_ID;
            view = null;
            return this;
        }

        public <T extends Container> T build(Class<T> clz) {
            if (view == null) {
                throw new IllegalArgumentException("Need rootView to bind a container");
            }
            try {
                Constructor<T> constructor = clz.getConstructor(int.class, View.class);
                return constructor.newInstance(containerId, view);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
