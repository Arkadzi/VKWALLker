package me.humennyi.arkadii.vkwallker.data;


import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.ProfileEntity;
import me.humennyi.arkadii.vkwallker.data.entities.UserEntity;
import me.humennyi.arkadii.vkwallker.data.mapper.Mapper;
import me.humennyi.arkadii.vkwallker.data.mapper.PostMapper;
import me.humennyi.arkadii.vkwallker.data.mapper.UserMapper;
import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;
import me.humennyi.arkadii.vkwallker.domain.User;
import rx.Observable;

/**
 * Created by arkadii on 11/5/16.
 */

public class SessionDataRepository implements SessionRepository {
    private final VkApi vkApi;
    private Mapper<UserEntity, User> userMapper;
    private Mapper<PostEntity, Post> postMapper;

    public SessionDataRepository(VkApi vkApi, Mapper<UserEntity, User> userMapper, Mapper<PostEntity, Post> postMapper) {
        this.vkApi = vkApi;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public Observable<User> getUserInfo(boolean rewrite) {
        return vkApi.getUserInfo(rewrite).map(userEntity -> userMapper.transform(userEntity));
    }

    @Override
    public Observable<List<Post>> getPosts(int offset, int count, boolean rewrite) {
        return vkApi.getPosts(rewrite, offset, count).map(items -> {
            List<Post> posts = new ArrayList<>();
            for (PostEntity item : items) {
                posts.add(postMapper.transform(item));
            }
            return posts;
        });
    }
}
