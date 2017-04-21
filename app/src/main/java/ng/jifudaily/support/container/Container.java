package ng.jifudaily.support.container;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;

/**
 * Created by Ng on 2017/4/21.
 */

public class Container implements LifeCycle {

    private View rootView;

    private int containerId;

    private boolean visibleToUser = true;

    private Container(int containerId, View rootView) {
        this.containerId = containerId;
        bind(rootView);
    }

    public Container bind(View rootView) {
        if (this.rootView != null && !(rootView instanceof ContainerView)) {
            throw new IllegalArgumentException("Need container view");
        }

        this.rootView = rootView;

        if (rootView != null) {
            onBind(this.rootView);
        }

        return this;
    }

    public int getContainerId() {
        return containerId;
    }

    public void onVisibleToUser(boolean isVisible) {
        visibleToUser = isVisible;
    }

    protected void onBind(View v) {
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

    public static final class Builder {
        private int containerId = -1;

        private View rootView = null;

        public Builder() {
        }

        public Builder id(int id) {
            containerId = id;
            return this;
        }

        public Builder bind(View view) {
            this.rootView = view;
            return this;
        }

        public Container build(){
            return new Container(containerId, rootView);
        }
    }


}
