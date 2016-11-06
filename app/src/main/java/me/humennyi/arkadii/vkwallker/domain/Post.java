package me.humennyi.arkadii.vkwallker.domain;

import java.util.Map;

/**
 * Created by arkadii on 11/5/16.
 */

public class Post {
    private String authorFirstName;
    private String authorLastName;
    private String authorPhotoUrl;
    private long date;
    private String text;
    private int likesCount;
    private int repostsCount;
    private boolean isRepost;
    private Map<String, Integer> attachments;

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public boolean isRepost() {
        return isRepost;
    }

    public void setRepost(boolean repost) {
        isRepost = repost;
    }

    public String getAuthorPhotoUrl() {
        return authorPhotoUrl;
    }

    public void setAuthorPhotoUrl(String authorPhotoUrl) {
        this.authorPhotoUrl = authorPhotoUrl;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

    public Map<String, Integer> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Integer> attachments) {
        this.attachments = attachments;
    }
}
