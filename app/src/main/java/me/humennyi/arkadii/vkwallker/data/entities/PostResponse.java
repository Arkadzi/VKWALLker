package me.humennyi.arkadii.vkwallker.data.entities;

import java.util.List;

/**
 * Created by arkadii on 11/8/16.
 */

public class PostResponse {
    public int count;
    public List<PostEntity> items;
    public List<ProfileEntity> profiles;

    @Override
    public String toString() {
        return "PostResponse{" +
                "count=" + count +
                ", items=" + items +
                ", profiles=" + profiles +
                '}';
    }
}
