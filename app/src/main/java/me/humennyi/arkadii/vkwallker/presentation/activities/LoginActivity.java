package me.humennyi.arkadii.vkwallker.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;

import me.humennyi.arkadii.vkwallker.R;
import me.humennyi.arkadii.vkwallker.VKWallApplication;
import me.humennyi.arkadii.vkwallker.presentation.ActivityNavigator;
import me.humennyi.arkadii.vkwallker.presentation.presenters.CredentialsPresenter;
import me.humennyi.arkadii.vkwallker.presentation.presenters.ICredenialsPresenter;
import me.humennyi.arkadii.vkwallker.presentation.views.ICredentialView;

public class LoginActivity extends FragmentActivity implements ICredentialView {

    @Inject
    ActivityNavigator activityNavigator;
    @Inject
    CredentialsPresenter presenter;
    private boolean isResumed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        VKWallApplication.getApp(this).getAppComponent().inject(this);
        presenter.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        presenter.onResume(
                VKSdk.isLoggedIn() ?
                        ICredenialsPresenter.LoginState.LOGGED_IN
                        : ICredenialsPresenter.LoginState.LOGGED_OUT
        );
    }

    @Override
    protected void onPause() {
        isResumed = false;
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                presenter.onLoggedIn(res);
            }

            @Override
            public void onError(VKError error) {
                presenter.onLoginError(error);
                Log.e("LoginActivity", String.valueOf(error));
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void showLoginForm() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null || !(fragment instanceof LoginFragment))
            manager.beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commitAllowingStateLoss();
    }

    @Override
    public void showLogoutForm() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null || !(fragment instanceof LogoutFragment))
            manager.beginTransaction()
                    .replace(R.id.fragment_container, new LogoutFragment())
                    .commitAllowingStateLoss();
    }

    @Override
    public void wakeUpSession() {
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                if (isResumed) {
                    switch (res) {
                        case LoggedOut:
                            presenter.onSessionWakeUp(ICredenialsPresenter.LoginState.LOGGED_OUT);
                            break;
                        case LoggedIn:
                            presenter.onSessionWakeUp(ICredenialsPresenter.LoginState.LOGGED_IN);
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {
                presenter.onLoginError(error);
            }
        });
    }

    @Override
    public void tryLogIn(String[] scope) {
        VKSdk.login(this, scope);
    }

    @Override
    public void navigateToWall() {
        activityNavigator.startWallActivity(this);
    }

    @Override
    public void tryLogOut() {
        VKSdk.logout();
        if (!VKSdk.isLoggedIn()) {
            showLoginForm();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static class LoginFragment extends android.support.v4.app.Fragment {
        @Inject
        CredentialsPresenter presenter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            VKWallApplication.getApp(getActivity()).getAppComponent().inject(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_login, container, false);
            v.findViewById(R.id.sign_in_button).setOnClickListener(view -> presenter.onLoginButtonClick());
            return v;
        }

    }

    public static class LogoutFragment extends android.support.v4.app.Fragment {
        @Inject
        CredentialsPresenter presenter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            VKWallApplication.getApp(getActivity()).getAppComponent().inject(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_logout, container, false);
            v.findViewById(R.id.continue_button).setOnClickListener(view -> presenter.onShowWallButtonClick());
            v.findViewById(R.id.logout).setOnClickListener(view -> presenter.onLogoutButtonClick());
            return v;
        }
    }
}