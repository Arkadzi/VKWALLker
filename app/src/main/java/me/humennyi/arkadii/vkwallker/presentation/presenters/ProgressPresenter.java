package me.humennyi.arkadii.vkwallker.presentation.presenters;

import android.util.Log;

import me.humennyi.arkadii.vkwallker.domain.subscribers.BaseProgressSubscriber;
import me.humennyi.arkadii.vkwallker.presentation.utils.Messages;
import me.humennyi.arkadii.vkwallker.presentation.views.IProgressView;


/**
 * Created by sebastian on 08.07.16.
 */
public class ProgressPresenter<V extends IProgressView> extends BasePresenter<V> implements
        BaseProgressSubscriber.ProgressSubscriberListener {


    public ProgressPresenter(Messages messages) {
        super(messages);
    }

    @Override
    public void onError(Throwable t) {
        V view = getView();
        if (view != null) {
            view.hideProgress();
            String error = getMessages().getError(t);
            view.showMessage(error);
        }
    }

    @Override
    public void onCompleted() {
        V view = getView();
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public void onStartLoading() {
        Log.e("progress", "onStartLoading");
        V view = getView();
        if (view != null) {
            view.showProgress();
        }
    }
}
