package me.humennyi.arkadii.vkwallker.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.humennyi.arkadii.vkwallker.data.api.VkApi;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.data.entities.UserEntity;
import me.humennyi.arkadii.vkwallker.data.mapper.Mapper;
import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.User;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;

/**
 * Created by arkadii on 11/9/16.
 */

public class SessionDataRepositoryTest {
    @Mock
    VkApi vkApi;
    @Mock
    Mapper<UserEntity, User> userMapper;
    @Mock
    Mapper<PostEntity, Post> postMapper;
    private SessionDataRepository sessionDataRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionDataRepository = new SessionDataRepository(vkApi, userMapper, postMapper);
    }

    @Test
    public void whenGetUserInfoSuccessThenReturnUser() {
        Mockito.when(vkApi.getUserInfo(anyBoolean())).thenReturn(Observable.just(new UserEntity()));
        Mockito.when(userMapper.transform(any(UserEntity.class))).thenReturn(new User());

        TestSubscriber<User> subscriber = new TestSubscriber<>();
        sessionDataRepository.getUserInfo(true).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents().size(), is(1));
    }

    @Test
    public void whenGetUserInfoFailureThenReturnError() {
        Mockito.when(vkApi.getUserInfo(anyBoolean())).thenReturn(Observable.create(
                subscriber -> {
                    subscriber.onError(new RuntimeException("Not Great"));
                }));
        Mockito.when(userMapper.transform(any(UserEntity.class))).thenReturn(new User());

        TestSubscriber<User> subscriber = new TestSubscriber<>();
        sessionDataRepository.getUserInfo(true).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnErrorEvents().size(), is(1));
    }

    @Test
    public void whenGetPostsFailureThenReturnError() {
        Mockito.when(vkApi.getPosts(anyBoolean(), anyInt(), anyInt())).thenReturn(Observable.create(
                subscriber -> {
                    subscriber.onError(new RuntimeException("Not Great"));
                }));
        Mockito.when(postMapper.transform(any(PostEntity.class))).thenReturn(new Post());

        TestSubscriber<List<Post>> subscriber = new TestSubscriber<>();
        sessionDataRepository.getPosts(1,1,true).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnErrorEvents().size(), is(1));
    }

    @Test
    public void whenGetPostsSuccessThenReturnPosts() {
        Mockito.when(vkApi.getUserInfo(anyBoolean())).thenReturn(Observable.create(
                subscriber -> {
                    subscriber.onError(new RuntimeException("Not Great"));
                }));
        Mockito.when(userMapper.transform(any(UserEntity.class))).thenReturn(new User());
        Mockito.when(vkApi.getPosts(anyBoolean(), anyInt(), anyInt())).thenReturn(Observable.just(new ArrayList<>()));
        Mockito.when(postMapper.transform(any(PostEntity.class))).thenReturn(new Post());

        TestSubscriber<List<Post>> subscriber = new TestSubscriber<>();
        sessionDataRepository.getPosts(1,1,true).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertThat(subscriber.getOnNextEvents().size(), is(1));
    }



}
