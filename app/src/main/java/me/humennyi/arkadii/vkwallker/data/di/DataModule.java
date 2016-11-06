package me.humennyi.arkadii.vkwallker.data.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.humennyi.arkadii.vkwallker.data.SessionDataRepository;
import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;


@Module
public class DataModule {

    @Provides
    @Singleton
    public VkApi provideVkApi() {
        return new VkApi();
    }

    @Provides
    @Singleton
    public SessionRepository provideSessionRepository(VkApi vkApi) {
        return new SessionDataRepository(vkApi);
    }
}
