package ng.jifudaily.support.ioc.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ng.jifudaily.support.util.UnitTool;

/**
 * Created by Ng on 2017/4/27.
 */
@Module(includes = {AppModule.class})
public class ToolModule {

    @Provides
    @Singleton
    public UnitTool providesUnitUtil(Application application) {
        return new UnitTool(application);
    }

}
