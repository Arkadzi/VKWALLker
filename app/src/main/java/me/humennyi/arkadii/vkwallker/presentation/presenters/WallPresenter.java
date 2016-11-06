package me.humennyi.arkadii.vkwallker.presentation.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.humennyi.arkadii.vkwallker.domain.VkInfo;
import me.humennyi.arkadii.vkwallker.domain.subscribers.BaseProgressSubscriber;
import me.humennyi.arkadii.vkwallker.domain.subscribers.SimpleSubscriberListener;
import me.humennyi.arkadii.vkwallker.domain.usecase.GetUserUseCase;
import me.humennyi.arkadii.vkwallker.presentation.utils.Messages;
import me.humennyi.arkadii.vkwallker.presentation.views.IWallView;

/**
 * Created by arkadii on 11/5/16.
 */
@Singleton
public class WallPresenter extends ProgressPresenter<IWallView> implements IWallPresenter {

    public static final int POST_COUNT = 10;
    private GetUserUseCase getUserUseCase;


    @Inject
    public WallPresenter(Messages messages, GetUserUseCase getUserUseCase) {
        super(messages);
        this.getUserUseCase = getUserUseCase;
        getUserUseCase.setCount(POST_COUNT);
    }

    @Override
    public void onCreate(IWallView view) {
        super.onCreate(view);
        if (getUserUseCase.hasCache()) {
            view.setData(getUserUseCase.getCache());
            if (getUserUseCase.isWorking()) {
                getUserUseCase.execute(getAppendQuerySubscriber());
            }
        } else {
            getUserUseCase.execute(getFirstQuerySubscriber());
        }

    }

    @Override
    public void onRelease() {
        getUserUseCase.unsubscribe();
        super.onRelease();
    }

    @NonNull
    private BaseProgressSubscriber<VkInfo> getAppendQuerySubscriber() {
        return new BaseProgressSubscriber<VkInfo>(new SimpleSubscriberListener() {
            @Override
            public void onStartLoading() {
                super.onStartLoading();
                IWallView view = getView();
                if (view != null) {
                    view.showAppendProgress();
                }
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                IWallView view = getView();
                if (view != null) {
                    view.hideAppendProgress();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                IWallView view = getView();
                if (view != null) {
                    view.hideAppendProgress();
                }
                showMessage(getMessages().getError(t));
            }
        }) {
            @Override
            public void onNext(VkInfo response) {
                super.onNext(response);
                IWallView view = getView();
                if (view != null) {
                    view.addData(response);
                }
            }
        };
    }

    @NonNull
    private BaseProgressSubscriber<VkInfo> getFirstQuerySubscriber() {
        return new BaseProgressSubscriber<VkInfo>(this) {
            @Override
            public void onNext(VkInfo response) {
                super.onNext(response);
                IWallView view = getView();
                if (view != null) {
                    view.setData(response);
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        getUserUseCase.unsubscribe();
        getUserUseCase.clear();
        getUserUseCase.execute(getFirstQuerySubscriber());
    }

    @Override
    public void onListScrolled() {
        VkInfo cache = getUserUseCase.getCache();
        if (!getUserUseCase.isWorking() && cache != null && cache.getCount() > getUserUseCase.getOffset()) {
            getUserUseCase.execute(getAppendQuerySubscriber());
        }
    }
}
