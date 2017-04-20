package ng.jifudaily.support.net.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ng on 2017/4/20.
 */

public class StoryBean extends BaseBean {
    private String title;
    @SerializedName("ga_prefix")
    private String gaPrefix;
    private List<String> images;
    private String image;
    @SerializedName("multipic")
    private String multiPic;
    private int type;
    private int id;

    public String getMultiPic() {
        return multiPic;
    }

    public void setMultiPic(String multiPic) {
        this.multiPic = multiPic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
