package me.humennyi.arkadii.vkwallker.presentation.presenters;


import android.support.annotation.Nullable;

import me.humennyi.arkadii.vkwallker.presentation.utils.Messages;
import me.humennyi.arkadii.vkwallker.presentation.views.IView;

/**
 * Created by sebastian on 14.06.16.
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {
    private final Messages messages;
    @Nullable
    private V view;

    public BasePresenter(Messages messages) {
        this.messages = messages;
    }

    @Nullable
    public V getView() {
        return view;
    }

    @Override
    public void onCreate(V view) {
        this.view = view;
    }

    @Override
    public void onRelease() {
        view = null;
    }

    protected Messages getMessages() {
        return messages;
    }

    protected void showMessage(int messageId) {
        V view = getView();
        if (view != null) {
            view.showMessage(messages.convertError(messageId));
        }
    }

    protected void showMessage(String message) {
        V view = getView();
        if (view != null) {
            view.showMessage(message);
        }
    }
}
