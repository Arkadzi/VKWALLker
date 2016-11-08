package me.humennyi.arkadii.vkwallker.data.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.humennyi.arkadii.vkwallker.data.SessionDataRepository;
import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.cache.DBHelper;
import me.humennyi.arkadii.vkwallker.domain.UserCache;
import me.humennyi.arkadii.vkwallker.data.cache.UserCacheImpl;
import me.humennyi.arkadii.vkwallker.domain.SessionRepository;


@Module
public class DataModule {

    @Provides
    @Singleton
    public DBHelper provideDBHelper(Context context) {
        return new DBHelper(context);
    }

    @Provides
    @Singleton
    public UserCache provideUserCache(DBHelper dbHelper, Context context){
        return new UserCacheImpl(dbHelper, context);
    }

    @Provides
    @Singleton
    public VkApi provideVkApi(UserCache userCache) {
        return new VkApi(userCache);
    }

    @Provides
    @Singleton
    public SessionRepository provideSessionRepository(VkApi vkApi) {
        return new SessionDataRepository(vkApi);
    }
}
