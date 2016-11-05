package me.humennyi.arkadii.vkwallker.presentation;

import android.app.Activity;
import android.content.Intent;

import javax.inject.Inject;

import me.humennyi.arkadii.vkwallker.presentation.activities.MainActivity;

/**
 * Created by arkadii on 11/5/16.
 */

public class ActivityNavigator {

    @Inject
    public ActivityNavigator() {

    }

    public void startWallActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }
}
