package me.humennyi.arkadii.vkwallker.domain.usecase;

import android.support.annotation.Nullable;

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
    private int count;
    private boolean forceNew=true;
    private boolean rewrite;
    private final VkInfo vkInfo = new VkInfo();

    @Inject
    public GetUserUseCase(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<VkInfo> getUseCaseObservable() {
        if (getCachedUser() == null) {
            return repository.getUserInfo(forceNew).flatMap(new Func1<User, Observable<List<Post>>>() {
                @Override
                public Observable<List<Post>> call(User user) {
                    return repository.getPosts(getOffset(), count, forceNew);
                }
            }, VkInfo::new).doOnNext(vkInfo -> {
                if (rewrite) {
                    vkInfo.setPosts(null);
                }
                rewrite = false;
                this.vkInfo.setUser(vkInfo.getUser());
                this.vkInfo.addPosts(vkInfo.getPosts());
            });
        } else {
            return repository.getPosts(getOffset(), count, forceNew)
                    .map(posts -> new VkInfo(null, posts))
                    .doOnNext(vkInfo1 -> {
                        rewrite = false;
                        this.vkInfo.addPosts(vkInfo1.getPosts());
                    });
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

//    public void setForceNew(boolean forceNew) {
//        this.forceNew = forceNew;
//    }

    public User getCachedUser() {
        return vkInfo.getUser();
    }

    public List<Post> getCachedPosts() {
        return vkInfo.getPosts();
    }

    private int getOffset() {
        if (getCachedPosts() == null) return 0;
        return getCachedPosts().size();
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;

    }

    public boolean hasCache() {
        return getCachedPosts() != null && getCachedUser() != null;
    }
}
