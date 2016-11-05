package me.humennyi.arkadii.vkwallker.presentation.views;

/**
 * Created by arkadii on 10/30/16.
 */

public interface ICredentialView extends IView {
    void showLoginForm();

    void showLogoutForm();

    void wakeUpSession();

    void tryLogIn(String[] scope);

    void navigateToWall();

    void tryLogOut();
}
