package me.humennyi.arkadii.vkwallker.data;


import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.ProfileEntity;
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
    private final UserMapper userMapper = new UserMapper();
    private final PostMapper postMapper = new PostMapper();

    public SessionDataRepository(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    @Override
    public Observable<User> getUserInfo(boolean forceRemote) {
        return vkApi.getUserInfo().map(userMapper::transform);
    }

    @Override
    public Observable<List<Post>> getPosts(int offset, int count, boolean forceUpdated) {
        return vkApi.getPosts(offset, count).map(items -> {
            List<Post> posts = new ArrayList<>();
            for (PostEntity item : items) {
                posts.add(postMapper.transform(item));
            }
            return posts;
        });
    }
}
