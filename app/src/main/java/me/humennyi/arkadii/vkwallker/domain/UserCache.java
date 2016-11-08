package me.humennyi.arkadii.vkwallker.domain;

import java.util.List;

import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.PostResponse;
import me.humennyi.arkadii.vkwallker.data.entities.UserEntity;
import rx.Observable;

/**
 * Created by arkadii on 11/7/16.
 */

public interface UserCache {
    Observable<UserEntity> getUser();
    void saveUser(UserEntity userEntity);
    Observable<List<PostEntity>> getPosts(int offset, int count);
    void savePosts(List<PostEntity> posts);
    void clearUser();
    void clearPosts();
}
