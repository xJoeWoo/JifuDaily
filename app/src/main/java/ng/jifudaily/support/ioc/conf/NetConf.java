package ng.jifudaily.support.ioc.conf;

/**
 * Created by Ng on 2017/4/20.
 */

public class NetConf {
    private final int TIMEOUT = 5000;

    private final String BASE_URL = "http://news-at.zhihu.com/api/4";

    private final String ACTION_NEWS = "news";
    private final String ACTION_STORY = "story";
    private final String ACTION_THEME = "theme";

    public int getNetTimeout() {
        return TIMEOUT;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getNewsAction() {
        return ACTION_NEWS;
    }

    public String getStoryAction() {
        return ACTION_STORY;
    }

    public String getThemeAction() {
        return ACTION_THEME;
    }
}
