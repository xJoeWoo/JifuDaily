package ng.jifudaily.support.ioc.conf;

/**
 * Created by Ng on 2017/4/20.
 */

public interface NetConf {
    int getNetTimeout();

    String getBaseUrl();

    String getNewsAction();

    String getStoryAction();

    String getThemeAction();
}
