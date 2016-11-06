package me.humennyi.arkadii.vkwallker.data.mapper;

import android.util.Log;

import me.humennyi.arkadii.vkwallker.data.UserEntity;
import me.humennyi.arkadii.vkwallker.domain.User;

/**
 * Created by arkadii on 11/5/16.
 */
public class UserMapper implements Mapper<UserEntity, User> {

    @Override
    public User transform(UserEntity obj) throws RuntimeException {
        User user = new User();
        user.setAbout(obj.getAbout());
        user.setActivities(obj.getActivities());
        user.setBirthDate(obj.getBirthDate());
        user.setCity(obj.getCity().getTitle());
        user.setFirstName(obj.getFirstName());
        user.setLastName(obj.getLastName());
        user.setInterests(obj.getInterests());
        user.setMobilePhone(obj.getMobilePhone());
        user.setPhotoUrl(obj.getPhotoUrl());
        user.setStatus(obj.getStatus());
        user.setId(obj.getId());
        Log.e("UserMapper", user.toString());
        return user;
    }
}
