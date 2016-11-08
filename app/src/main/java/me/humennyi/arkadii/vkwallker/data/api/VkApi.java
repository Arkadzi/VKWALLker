package me.humennyi.arkadii.vkwallker.data.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

import me.humennyi.arkadii.vkwallker.data.cache.UserCache;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.PostResponse;
import me.humennyi.arkadii.vkwallker.data.entities.ProfileEntity;
import me.humennyi.arkadii.vkwallker.data.entities.UserEntity;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by arkadii on 11/5/16.
 */

public class VkApi {

    private final String USER_INFO_REQUEST_FIELDS = "photo_200, about, activities, bdate, career, contacts, city, status, interests";
    private final Gson gson = new Gson();
    private final UserCache userCache;

    public VkApi(UserCache userCache) {
        this.userCache = userCache;
    }

    public Observable<UserEntity> getUserInfo() {
        return userCache.getUser().flatMap(userEntity -> {
            if (userEntity != null) return Observable.just(userEntity);
            return getRemoteUserInfo();
        });
    }

    private Observable<UserEntity> getRemoteUserInfo() {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, USER_INFO_REQUEST_FIELDS));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e("VkApi", response.responseString);
                        Response<List<UserEntity>> userResponse = gson.fromJson(response.responseString,
                                new TypeToken<Response<List<UserEntity>>>() {
                                }.getType());
                        UserEntity userEntity = userResponse.response.get(0);
                        Log.e("VkApi", userEntity.toString());
                        subscriber.onNext(userEntity);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(VKError error) {
                        subscriber.onError(new Throwable(error.errorMessage));
                    }
                });
            }
        }).doOnNext(userCache::saveUser);
    }

    public Observable<List<PostEntity>> getPosts(int offset, int count) {
        return userCache.getPosts(offset, count).flatMap(postEntities -> {
            Log.e("VkApi", "getPosts " + postEntities);
            if (postEntities != null) return Observable.just(postEntities);
            return getRemotePosts(offset, count);
        });
    }

    @NonNull
    private Observable<List<PostEntity>> getRemotePosts(final int offset, final int count) {
        return Observable.create(new Observable.OnSubscribe<List<PostEntity>>() {
            @Override
            public void call(Subscriber<? super List<PostEntity>> subscriber) {
                VKRequest request = VKApi.wall().get(VKParameters.from(
                        VKApiConst.EXTENDED, 1,
                        VKApiConst.COUNT, count,
                        VKApiConst.OFFSET, offset));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e("VkApi", response.responseString);
                        Response<PostResponse> userResponse = gson.fromJson(response.responseString,
                                new TypeToken<Response<PostResponse>>() {
                                }.getType());

                        PostResponse postResponse = userResponse.response;
                        Log.e("VkApi", postResponse.toString());
                        handleProfiles(postResponse);
                        subscriber.onNext(postResponse.items);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(VKError error) {
                        Log.e("VkApi", error.toString());
                        subscriber.onError(new Throwable(error.errorMessage));
                    }
                });
            }
        }).doOnNext(userCache::savePosts);
    }

    private void handleProfiles(PostResponse postResponse) {
        List<PostEntity> items = postResponse.items;
        List<ProfileEntity> profiles = postResponse.profiles;
        for (PostEntity item : items) {
            int i = profiles.indexOf(new ProfileEntity(item.getFromId()));
            ProfileEntity postAuthor = profiles.get(i);
            item.setFromFirstName(postAuthor.getFirstName());
            item.setFromLastName(postAuthor.getLastName());
            item.setFromPhoto(postAuthor.getPhoto());
        }
    }

    private static class Response<T> {
        T response;
    }
}
