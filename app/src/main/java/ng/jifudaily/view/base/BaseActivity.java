package ng.jifudaily.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import ng.jifudaily.support.container.ContainerActivity;
import ng.jifudaily.support.ioc.service.ServiceCollection;

/**
 * Created by Ng on 2017/4/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements ContainerActivity{

    @Inject
    ServiceCollection services;

    protected ServiceCollection getServices() {
        return services;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((App) getApplication()).getServiceComponent().injectTo(this);
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
}
