package me.humennyi.arkadii.vkwallker.presentation.presenters;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

import me.humennyi.arkadii.vkwallker.presentation.views.ICredentialView;

/**
 * Created by arkadii on 11/5/16.
 */

public interface ICredenialsPresenter extends IPresenter<ICredentialView> {
    void onSessionWakeUp(LoginState result);

    void onResume(LoginState loginState);

    void onLoginButtonClick();

    void onShowWallButtonClick();

    void onLogoutButtonClick();

    void onLoggedIn(VKAccessToken res);

    void onLoginError(VKError error);

    enum LoginState {LOGGED_IN, LOGGED_OUT}
}
