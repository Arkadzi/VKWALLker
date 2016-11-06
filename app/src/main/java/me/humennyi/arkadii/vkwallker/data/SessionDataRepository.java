package me.humennyi.arkadii.vkwallker.data;


import android.support.v4.util.Pair;
import android.util.Log;

import com.vk.sdk.api.VKApi;

import java.util.ArrayList;
import java.util.List;

import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.mapper.PostMapper;
import me.humennyi.arkadii.vkwallker.data.mapper.UserMapper;
import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;
import me.humennyi.arkadii.vkwallker.domain.User;
import rx.Observable;
import rx.functions.Func1;

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
    public Observable<User> getUserInfo() {
        return vkApi.geUserInfo().map(userMapper::transform);
    }

    @Override
    public Observable<Pair<List<Post>, Integer>> getPosts(int offset, int count) {
        Log.e("SessiondataRepository", "offset = " + offset + " count = " + count);
        return vkApi.getPosts(offset, count).map(postResponse -> {
            List<Post> posts = new ArrayList<>();

            List<PostEntity> items = postResponse.items;
            List<ProfileEntity> profiles = postResponse.profiles;
            for (PostEntity item : items) {
                int i = profiles.indexOf(new ProfileEntity(item.getFromId()));
                ProfileEntity postAuthor = profiles.get(i);
                item.setFromFirstName(postAuthor.getFirstName());
                item.setFromLastName(postAuthor.getLastName());
                item.setFromPhoto(postAuthor.getPhoto());
                posts.add(postMapper.transform(item));
            }

            return new Pair<>(posts, postResponse.count);
        });
    }
}
