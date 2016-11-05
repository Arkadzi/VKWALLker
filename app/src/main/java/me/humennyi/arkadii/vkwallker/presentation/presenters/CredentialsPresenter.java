package me.humennyi.arkadii.vkwallker.presentation.presenters;

import android.text.TextUtils;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.humennyi.arkadii.vkwallker.Constants;
import me.humennyi.arkadii.vkwallker.presentation.utils.Messages;
import me.humennyi.arkadii.vkwallker.presentation.views.ICredentialView;

/**
 * Created by arkadii on 11/5/16.
 */

@Singleton
public class CredentialsPresenter extends BasePresenter<ICredentialView> implements ICredenialsPresenter {

    @Inject
    public CredentialsPresenter(Messages messages) {
        super(messages);
    }


    @Override
    public void onCreate(ICredentialView view) {
        super.onCreate(view);
        view.wakeUpSession();
    }

    @Override
    public void onSessionWakeUp(LoginState result) {
        ICredentialView view = getView();
        if (view != null) {
            switch (result) {
                case LOGGED_OUT:
                    view.showLoginForm();
                    break;
                case LOGGED_IN:
                    view.showLogoutForm();
                    break;
            }
        }
    }

    @Override
    public void onResume(LoginState loginState) {
        onSessionWakeUp(loginState);
    }

    @Override
    public void onLoginButtonClick() {
        ICredentialView view = getView();
        if (view != null) {
            view.tryLogIn(Constants.SCOPE);
        }
    }

    @Override
    public void onShowWallButtonClick() {
        ICredentialView view = getView();
        if (view != null) {
            view.navigateToWall();
        }
    }

    @Override
    public void onLogoutButtonClick() {
        ICredentialView view = getView();
        if (view != null) {
            view.tryLogOut();
        }
    }

    @Override
    public void onLoggedIn(VKAccessToken res) {
        onShowWallButtonClick();
    }

    @Override
    public void onLoginError(VKError error) {
        if (!TextUtils.isEmpty(error.errorMessage)) {
            showMessage(error.errorMessage);
        }
    }
}
