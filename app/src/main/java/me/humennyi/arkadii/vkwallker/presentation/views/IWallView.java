package me.humennyi.arkadii.vkwallker.presentation.views;

import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;

/**
 * Created by arkadii on 11/5/16.
 */
public interface IWallView extends IProgressView {

    void showAppendProgress();

    void hideAppendProgress();

    void setData(VkInfo response);

    void addData(VkInfo response);
}
