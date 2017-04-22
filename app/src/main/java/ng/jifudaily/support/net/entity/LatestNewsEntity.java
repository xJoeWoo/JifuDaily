package ng.jifudaily.support.net.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ng on 2017/4/20.
 */

public class LatestNewsEntity extends BaseEntity {
    private String date;
    private List<StoryEntity> stories;
    @SerializedName("top_stories")
    private List<StoryEntity> topStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoryEntity> stories) {
        this.stories = stories;
    }

    public List<StoryEntity> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<StoryEntity> topStories) {
        this.topStories = topStories;
    }
}
