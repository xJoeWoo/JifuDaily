package ng.jifudaily.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ng.jifudaily.support.ioc.service.ServiceCollection;

/**
 * Created by Ng on 2017/4/20.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    ServiceCollection services;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((App) getApplication()).getActivityComponent().injectBaseActivity(this);
    }

    public ServiceCollection getServices() {
        return services;
    }

}
