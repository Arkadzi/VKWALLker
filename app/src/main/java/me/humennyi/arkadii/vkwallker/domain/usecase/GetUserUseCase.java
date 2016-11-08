package me.humennyi.arkadii.vkwallker.domain.usecase;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;
import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by arkadii on 11/5/16.
 */
@Singleton
public class GetUserUseCase extends UseCase<VkInfo> {
    private final SessionRepository repository;
    private int count;
    private boolean rewrite;
    private final VkInfo vkInfo = new VkInfo();

    @Inject
    public GetUserUseCase(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<VkInfo> getUseCaseObservable() {
        if (rewrite || getCachedUser() == null) {
            return repository.getUserInfo(rewrite).flatMap(new Func1<User, Observable<List<Post>>>() {
                @Override
                public Observable<List<Post>> call(User user) {
                    return repository.getPosts(getOffset(), count, rewrite);
                }
            }, VkInfo::new).doOnNext(vkInfo1 -> {
                Log.e("GetUserUseCase", "map");
                if (rewrite) {
                    vkInfo.setPosts(null);
                }
                rewrite = false;
                Log.e("GetUserUseCase", "map " + 2);
                this.vkInfo.setUser(vkInfo1.getUser());
                Log.e("GetUserUseCase", "map " + 3);
                this.vkInfo.addPosts(vkInfo1.getPosts());
                Log.e("GetUserUseCase", "map " + 4);
            });
        } else {
            return repository.getPosts(getOffset(), count, rewrite)
                    .map(posts -> new VkInfo(null, posts))
                    .doOnNext(vkInfo1 -> {
                        rewrite = false;
                        this.vkInfo.addPosts(vkInfo1.getPosts());
                    });
        }
    }

    @Override
    protected void onError() {
        super.onError();
        rewrite = false;
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
        if (rewrite || getCachedPosts() == null) return 0;
        return getCachedPosts().size();
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;

    }

    public boolean isRewrite() {
        return rewrite;
    }

    public boolean hasCache() {
        return getCachedPosts() != null && getCachedUser() != null;
    }

    public void clear() {
        rewrite = false;
        vkInfo.setUser(null);
        vkInfo.setPosts(null);
    }
}
