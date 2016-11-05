package me.humennyi.arkadii.vkwallker.di;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.humennyi.arkadii.vkwallker.VKWallApplication;
import me.humennyi.arkadii.vkwallker.data.di.DataModule;


@Module(includes = DataModule.class)
public class ApplicationModule {
    private final VKWallApplication application;

    public ApplicationModule(VKWallApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }
}
