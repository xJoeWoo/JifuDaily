package ng.jifudaily.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;
import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.container.ContainerManager;
import ng.jifudaily.support.container.ContainerSwitcher;
import ng.jifudaily.support.ioc.service.ServiceCollection;
import ng.jifudaily.view.container.LatestNewsContainer;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerActivity extends BaseActivity {

    @Inject
    Lazy<ContainerManager> containerManager;

    private boolean isContainerManagerInited = false;

//    @Inject
//    Provider<Container> containerProvider;

    //
//    public ContainerActivity() {
//        ((App) getApplication()).getActivityComponent().inject(this);
//    }
    protected ContainerManager getContainerManager() {
        if (!isContainerManagerInited) {
            containerManager.get().bind(this);
            isContainerManagerInited = true;
        }
        return containerManager.get();
    }

    protected <T extends Container<T>> T createContainer(Class<T> clz) {
        try {
            T c = clz.newInstance();
            getContainerManager().add(c);
            return c;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
//        try {
//            Constructor<T> constructor =  .getConstructor();
//            constructor.setAccessible(true);
//            return constructor.newInstance().manager(containerManager);
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    protected ContainerSwitcher getContainerSwitcher() {
        return getContainerManager().getSwitcher();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getActivityComponent().injectContainerActivity(this);
        getContainerManager().bind(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getContainerManager().callContainers(c -> c.onNewIntent(intent));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getContainerManager().callContainers(c -> c.onSaveInstanceState(outState));

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        getContainerManager().callContainers(c -> c.onRestoreInstanceState(savedInstanceState));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getContainerManager().callContainers(Container::onStart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContainerManager().callContainers(Container::onResume);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContainerManager().callContainers(Container::onPause);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getContainerManager().callContainers(Container::onStop);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getContainerManager().callContainers(Container::onStart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContainerManager().callContainers(Container::onDestroy);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getContainerManager().callContainers(c -> c.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    public void onBackPressed() {
        if (!getContainerManager().onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getContainerManager().onBackPressed();
        return false;
//        return super.onSupportNavigateUp();
    }
}
