package ng.jifudaily.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Provider;

import ng.jifudaily.support.container.Container;
import ng.jifudaily.support.container.ContainerCallback;
import ng.jifudaily.support.container.ContainerManager;
import ng.jifudaily.support.container.ContainerSwitcher;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerActivity extends BaseActivity {

    @Inject
    ContainerManager containerManager;

    @Inject
    Provider<Container.Builder> builderProvider;

    @Inject
    Provider<Container> containerProvider;

    @Inject
    Provider<ContainerCallback> callbackProvider;

    //
//    public ContainerActivity() {
//        ((App) getApplication()).getActivityComponent().inject(this);
//    }
    protected ContainerManager getContainerManager() {
        return containerManager;
    }

    protected Container.Builder getContainerBuilder() {
        return builderProvider.get();
    }

    protected Container createDefaultContainer() {
        return containerProvider.get();
    }

    protected ContainerCallback createContainerCallback() {
        return callbackProvider.get();
    }

    protected ContainerSwitcher getContainerSwitcher() {
        return containerManager.getSwitcher();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getActivityComponent().injectContainerActivity(this);
        containerManager.bind(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        containerManager.callContainers(c -> c.onNewIntent(intent));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        containerManager.callContainers(c -> c.onSaveInstanceState(outState));

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        containerManager.callContainers(c -> c.onRestoreInstanceState(savedInstanceState));
    }

    @Override
    protected void onStart() {
        super.onStart();
        containerManager.callContainers(Container::onStart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerManager.callContainers(Container::onResume);
    }

    @Override
    protected void onPause() {
        super.onPause();
        containerManager.callContainers(Container::onPause);
    }

    @Override
    protected void onStop() {
        super.onStop();
        containerManager.callContainers(Container::onStop);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        containerManager.callContainers(Container::onStart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        containerManager.callContainers(Container::onDestroy);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        containerManager.callContainers(c -> c.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        containerManager.callContainers(Container::onBackPressed);
    }
}
