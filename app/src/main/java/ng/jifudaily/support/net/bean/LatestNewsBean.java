package ng.jifudaily.support.net.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ng on 2017/4/20.
 */

public class LatestNewsBean extends BaseBean {
    private String date;
    private List<StoryBean> stories;
    @SerializedName("top_stories")
    private List<StoryBean> topStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public List<StoryBean> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<StoryBean> topStories) {
        this.topStories = topStories;
    }
}
