package me.humennyi.arkadii.vkwallker.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadii on 11/5/16.
 */

public class VkInfo {
    private User user;
    private List<Post> posts;

    public VkInfo(User user, List<Post> posts) {
        this.user = user;
        this.posts = posts;
    }

    public VkInfo() {

    }

    public User getUser() {
        return user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPosts(List<Post> posts) {
        if (this.posts == null) {
            this.posts = new ArrayList<>();
        }
        this.posts.addAll(posts);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "VkInfo{" +
                "user=" + user +
                ", posts=" + posts +
                '}';
    }
}
