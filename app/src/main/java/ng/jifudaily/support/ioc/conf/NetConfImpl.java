package ng.jifudaily.support.ioc.conf;

/**
 * Created by Ng on 2017/4/20.
 */

public class NetConfImpl implements NetConf {
    private final int TIMEOUT = 10000;

    private final String BASE_URL = "http://news-at.zhihu.com/api/4";

    private final String ACTION_NEWS = "news";
    private final String ACTION_STORY = "story";
    private final String ACTION_THEME = "theme";

    @Override
    public int getNetTimeout() {
        return TIMEOUT;
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public String getNewsAction() {
        return ACTION_NEWS;
    }

    @Override
    public String getStoryAction() {
        return ACTION_STORY;
    }

    @Override
    public String getThemeAction() {
        return ACTION_THEME;
    }
}
