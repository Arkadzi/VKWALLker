package me.humennyi.arkadii.vkwallker.di;

import javax.inject.Singleton;

import dagger.Component;
import me.humennyi.arkadii.vkwallker.presentation.activities.LoginActivity;
import me.humennyi.arkadii.vkwallker.presentation.activities.MainActivity;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoginActivity.LoginFragment loginFragment);

    void inject(LoginActivity.LogoutFragment logoutFragment);

    void inject(MainActivity mainActivity);
}
