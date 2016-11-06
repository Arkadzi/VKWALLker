package me.humennyi.arkadii.vkwallker.data.api;

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

import me.humennyi.arkadii.vkwallker.data.PostEntity;
import me.humennyi.arkadii.vkwallker.data.ProfileEntity;
import me.humennyi.arkadii.vkwallker.data.UserEntity;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by arkadii on 11/5/16.
 */

public class VkApi {

    private final String USER_INFO_REQUEST_FIELDS = "photo_200, about, activities, bdate, career, contacts, city, status, interests";
    private final Gson gson = new Gson();

    public Observable<UserEntity> geUserInfo() {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, USER_INFO_REQUEST_FIELDS));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e("VkApi", response.responseString);
//                        List<UserEntity>
                        Response<List<UserEntity>> userResponse = gson.fromJson(response.responseString,
                                new TypeToken<Response<List<UserEntity>>>(){}.getType());
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
        });
    }

    public Observable<PostResponse> getPosts(int offset, int count) {
        return Observable.create(new Observable.OnSubscribe<PostResponse>() {
            @Override
            public void call(Subscriber<? super PostResponse> subscriber) {
                VKRequest request = VKApi.wall().get(VKParameters.from(
                        VKApiConst.EXTENDED, 1,
                        VKApiConst.COUNT, count,
                        VKApiConst.OFFSET, offset));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e("VkApi", response.responseString);
                        Response<PostResponse> userResponse = gson.fromJson(response.responseString,
                                new TypeToken<Response<PostResponse>>(){}.getType());
                        PostResponse postResponse = userResponse.response;
                        Log.e("VkApi", postResponse.toString());
                        subscriber.onNext(postResponse);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(VKError error) {
                        subscriber.onError(new Throwable(error.errorMessage));
                    }
                });
            }
        });
    }

    static class Response<T> {
        T response;
    }



    public static class PostResponse {
        public int count;
        public List<PostEntity> items;
        public List<ProfileEntity> profiles;

        @Override
        public String toString() {
            return "PostResponse{" +
                    "count=" + count +
                    ", items=" + items +
                    ", profiles=" + profiles +
                    '}';
        }
    }
}
