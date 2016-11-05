package me.humennyi.arkadii.vkwallker.presentation.presenters;


import me.humennyi.arkadii.vkwallker.presentation.views.IView;

/**
 * Created by sebastian on 07.07.16.
 */
public interface IPresenter<V extends IView> {
    void onCreate(V view);
    void onRelease();
}
