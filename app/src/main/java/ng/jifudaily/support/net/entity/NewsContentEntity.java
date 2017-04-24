package ng.jifudaily.support.net.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ng on 2017/4/20.
 */

public class NewsContentEntity extends BaseEntity {

    @SerializedName("body")
    private String contentBody;

    @SerializedName("image_source")
    private String imageSource;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("share_url")
    private String shareUrl;

    @SerializedName("js")
    private List<String> jsUrls;

    private List<AvatarBean> recommenders;

    class AvatarBean {

        @SerializedName("avatar")
        private String AvatarUrl;

        public String getAvatarUrl() {
            return AvatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            AvatarUrl = avatarUrl;
        }
    }

    @SerializedName("ga_prefix")
    private String gaPrefix;

    private SectionBean section;

    class SectionBean {

        @SerializedName("thumbnail")
        private String thumbnailUrl;

        private int id;
        private String name;

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private int type;

    private int id;

    private String title;

    public String getTitle() {
        return title;
    }

    public NewsContentEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    @SerializedName("css")
    private List<String> cssUrls;

    // outter articles
    @SerializedName("theme_name")
    private String themeName;

    @SerializedName("editor_name")
    private String editorName;

    @SerializedName("theme_id")
    private int themeId;

    public String completeHTML;

    public String getCompleteHTML() {
        return completeHTML;
    }

    public NewsContentEntity setCompleteHTML(String completeHTML) {
        this.completeHTML = completeHTML;
        return this;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<String> getJsUrls() {
        return jsUrls;
    }

    public void setJsUrls(List<String> jsUrls) {
        this.jsUrls = jsUrls;
    }

    public List<AvatarBean> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<AvatarBean> recommenders) {
        this.recommenders = recommenders;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public SectionBean getSection() {
        return section;
    }

    public void setSection(SectionBean section) {
        this.section = section;
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

    public List<String> getCssUrls() {
        return cssUrls;
    }

    public void setCssUrls(List<String> cssUrls) {
        this.cssUrls = cssUrls;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
}
