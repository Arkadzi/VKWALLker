package me.humennyi.arkadii.vkwallker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import me.humennyi.arkadii.vkwallker.di.ApplicationComponent;
import me.humennyi.arkadii.vkwallker.di.ApplicationModule;
import me.humennyi.arkadii.vkwallker.di.DaggerApplicationComponent;
import me.humennyi.arkadii.vkwallker.presentation.activities.LoginActivity;

/**
 * Created by arkadii on 11/4/16.
 */

public class VKWallApplication extends Application {
    private ApplicationComponent component;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(VKWallApplication.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VKWallApplication.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        buildAppComponent();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }


    private void buildAppComponent() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return component;
    }

    public static VKWallApplication getApp(Context context) {
        return (VKWallApplication) context.getApplicationContext();
    }
}
