package me.humennyi.arkadii.vkwallker.domain;

import java.util.List;

/**
 * Created by arkadii on 11/5/16.
 */

public class VkInfo {
    private User user;
    private List<Post> post;
    private int count;

    public VkInfo(User user, List<Post> post, int count) {
        this.user = user;
        this.post = post;
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public List<Post> getPost() {
        return post;
    }

    public int getCount() {
        return count;
    }
}
