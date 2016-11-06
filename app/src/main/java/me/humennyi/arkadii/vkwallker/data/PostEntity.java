package me.humennyi.arkadii.vkwallker.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkadii on 11/5/16.
 */

public class PostEntity {
    @SerializedName("from_id")
    private String fromId;
    private long date;
    private String text;
    private LikeEntity likes;
    private LikeEntity reposts;
    private List<AttachmentEntity> attachments;
    private String fromFirstName;
    private String fromLastName;
    private String fromPhoto;
    @SerializedName("copy_history")
    private List<PostEntity> copyHistory;

    public List<PostEntity> getCopyHistory() {
        return copyHistory;
    }

    public String getFromPhoto() {
        return fromPhoto;
    }

    public void setFromPhoto(String fromPhoto) {
        this.fromPhoto = fromPhoto;
    }

    public String getFromFirstName() {
        return fromFirstName;
    }

    public void setFromFirstName(String fromFirstName) {
        this.fromFirstName = fromFirstName;
    }

    public String getFromLastName() {
        return fromLastName;
    }

    public void setFromLastName(String fromLastName) {
        this.fromLastName = fromLastName;
    }

    public String getFromId() {
        return fromId;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public LikeEntity getLikes() {
        return likes;
    }

    public LikeEntity getReposts() {
        return reposts;
    }

    public List<AttachmentEntity> getAttachments() {
        return attachments;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "fromId='" + fromId + '\'' +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", reposts=" + reposts +
                ", attachments=" + attachments +
                ", fromFirstName='" + fromFirstName + '\'' +
                ", fromLastName='" + fromLastName + '\'' +
                ", fromPhoto='" + fromPhoto + '\'' +
                '}';
    }
}
