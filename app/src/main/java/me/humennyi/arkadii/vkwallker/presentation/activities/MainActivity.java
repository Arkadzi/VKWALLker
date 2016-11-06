package me.humennyi.arkadii.vkwallker.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.humennyi.arkadii.vkwallker.R;
import me.humennyi.arkadii.vkwallker.VKWallApplication;
import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;
import me.humennyi.arkadii.vkwallker.presentation.adapters.WallAdapter;
import me.humennyi.arkadii.vkwallker.presentation.presenters.WallPresenter;
import me.humennyi.arkadii.vkwallker.presentation.views.IWallView;

public class MainActivity extends AppCompatActivity implements IWallView {

    @BindView(R.id.content_view)
    View contentView;
    @BindView(R.id.progress_bar)
    View progressBar;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.avatar)
    ImageView avatarView;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Inject
    WallPresenter presenter;
    private WallAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean paginateEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VKWallApplication.getApp(this).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        paginateEnabled = true;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WallAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int state;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                state = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                int totalCount = layoutManager.getItemCount();
                if (paginateEnabled
                        && lastVisible == totalCount - 1
                        && state != 0
                        && dy > 0
                        ) {
                    presenter.onListScrolled();
                }
            }
        });
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            presenter.onRefresh();
        });
        presenter.onCreate(this);
    }


    @Override
    public void showAppendProgress() {
        paginateEnabled = false;
        adapter.setProgress(true);
    }

    @Override
    public void hideAppendProgress() {
        paginateEnabled = true;
        adapter.setProgress(false);
    }

    @Override
    public void setData(VkInfo response) {
        User user = response.getUser();
        Picasso.with(this).load(user.getPhotoUrl()).into(avatarView);
        collapsingToolbarLayout.setTitle(user.getFirstName() + " " + user.getLastName());
        adapter.setData(response);
    }

    @Override
    public void addData(VkInfo response) {
        adapter.appendPosts(response.getPost());
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        contentView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        contentView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
