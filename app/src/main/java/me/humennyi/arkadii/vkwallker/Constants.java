package me.humennyi.arkadii.vkwallker;

import com.vk.sdk.VKScope;

/**
 * Created by arkadii on 11/5/16.
 */

public interface Constants {

    String[] SCOPE = new String[]{
            VKScope.WALL,
            VKScope.EMAIL,
            VKScope.STATUS,
            VKScope.FRIENDS,
            VKScope.AUDIO,
            VKScope.VIDEO,
            VKScope.PHOTOS
    };
}
