package me.humennyi.arkadii.vkwallker.domain;


import android.support.v4.util.Pair;

import java.util.List;

import rx.Observable;

/**
 * Created by arkadii on 11/5/16.
 */

public interface SessionRepository {
    Observable<User> getUserInfo();
    Observable<Pair<List<Post>, Integer>> getPosts(int offset, int count);
}
