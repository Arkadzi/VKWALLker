package me.humennyi.arkadii.vkwallker.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.UserEntity;
import me.humennyi.arkadii.vkwallker.domain.UserCache;
import rx.Observable;
import rx.Subscriber;

public class UserCacheImpl implements UserCache {

    public static final String USER = "user";
    private final DBHelper dbHelper;
    private final Context context;
    private final Gson gson = new Gson();

    public UserCacheImpl(DBHelper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }


    @Override
    public Observable<UserEntity> getUser() {

        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
                String userJson = sharedPreferences.getString(USER, null);
                UserEntity userEntity = null;
                if (userJson != null) {
                    userEntity = gson.fromJson(userJson, UserEntity.class);
                }
                subscriber.onNext(userEntity);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(USER, gson.toJson(userEntity)).apply();
    }

    @Override
    public Observable<List<PostEntity>> getPosts(int offset, int count) {
        return Observable.create(new Observable.OnSubscribe<List<PostEntity>>() {
            @Override
            public void call(Subscriber<? super List<PostEntity>> subscriber) {
                List<PostEntity> withOffset = dbHelper.getWithOffset(offset, count);
                if (withOffset.isEmpty()) withOffset = null;
                subscriber.onNext(withOffset);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void savePosts(List<PostEntity> posts) {
        for (PostEntity post : posts) {
            dbHelper.insert(post);
        }
    }

    @Override
    public void clearPosts() {
        dbHelper.deletePosts();
    }

    @Override
    public void clearUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
