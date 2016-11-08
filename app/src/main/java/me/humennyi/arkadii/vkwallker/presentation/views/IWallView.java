package me.humennyi.arkadii.vkwallker.presentation.views;

import java.util.List;

import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;

/**
 * Created by arkadii on 11/5/16.
 */
public interface IWallView extends IProgressView {

    void showAppendProgress();

    void hideAppendProgress();

    void addData(List<Post> response);

    void setData(User cachedUser, List<Post> cachedPosts);

    void showRefresh();
    void hideRefresh();
}
