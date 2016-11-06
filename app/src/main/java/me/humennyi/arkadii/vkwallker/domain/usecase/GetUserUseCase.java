package me.humennyi.arkadii.vkwallker.domain.usecase;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;
import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by arkadii on 11/5/16.
 */
public class GetUserUseCase extends UseCase<VkInfo> {
    private final SessionRepository repository;
    @Nullable
    private VkInfo vkInfo;
    private int count;
    private int offset;

    @Inject
    public GetUserUseCase(SessionRepository repository) {
        this.repository = repository;
    }


    @Override
    protected Observable<VkInfo> getUseCaseObservable() {
        return repository.getUserInfo().flatMap(new Func1<User, Observable<Pair<List<Post>, Integer>>>() {
            @Override
            public Observable<Pair<List<Post>, Integer>> call(User user) {
                return repository.getPosts(offset, count);
            }
        }, (user1, pair) -> new VkInfo(user1, pair.first, pair.second))
                .doOnNext(vkInfo -> {
                    if (this.vkInfo == null) {
                        this.vkInfo = vkInfo;
                    } else {
                        this.vkInfo.getPost().addAll(vkInfo.getPost());
                    }
                    this.offset = this.vkInfo.getPost().size();
                });
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Nullable
    public VkInfo getCache() {
        return vkInfo;
    }
    public boolean hasCache() {
        return vkInfo != null;
    }

    public int getOffset() {
        return offset;
    }

    public void clear() {
        offset = 0;
        vkInfo = null;
    }
}
