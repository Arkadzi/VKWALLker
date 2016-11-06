package me.humennyi.arkadii.vkwallker.presentation.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.humennyi.arkadii.vkwallker.R;
import me.humennyi.arkadii.vkwallker.domain.Post;
import me.humennyi.arkadii.vkwallker.domain.User;
import me.humennyi.arkadii.vkwallker.domain.VkInfo;

public class WallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String PROGRESS = "progress";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private final int SECONDARY_TEXT_COLOR;
    private final int ACCENT_COLOR;
    private final List<Object> data = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
    private final Context context;
    private boolean inProgress;


    public WallAdapter(Context context) {
        this.context = context;
        ACCENT_COLOR = getColor(context, R.color.colorAccent);
        SECONDARY_TEXT_COLOR = getColor(context, R.color.textColorSecondary);
    }

    public void setProgress(boolean progress) {
        try {
            if (progress != inProgress && !data.isEmpty()) {
                if (progress) {
                    data.remove(PROGRESS);
                    data.add(PROGRESS);
                    notifyItemInserted(data.size() - 1);
                } else {
                    int position = data.indexOf(PROGRESS);
                    if (position != -1 && data.remove(PROGRESS)) {
                        notifyItemRemoved(position);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("adapter", String.valueOf(e));
        }
        inProgress = progress;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post_layout, parent, false);
            PostViewHolder postViewHolder = new PostViewHolder(view);
            tint(postViewHolder.attachments, R.drawable.ic_attachment_white_18dp, ACCENT_COLOR);
            tint(postViewHolder.likesCount, R.drawable.ic_favorite_white_18dp, SECONDARY_TEXT_COLOR);
            tint(postViewHolder.repostsCount, R.drawable.ic_reply_white_18dp, SECONDARY_TEXT_COLOR);
            return postViewHolder;
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_info_layout, parent, false);
            return new UserInfoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress_layout, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (viewHolder instanceof UserInfoViewHolder && data.get(adapterPosition) instanceof User) {
            UserInfoViewHolder holder = (UserInfoViewHolder) viewHolder;
            User user = (User) data.get(adapterPosition);
            holder.cityView.setText(user.getCity());
            holder.about.setText(user.getAbout().trim());
            holder.activities.setText(user.getActivities().trim());
            String status = TextUtils.isEmpty(user.getStatus()) ?
                    context.getString(R.string.noStatus) : String.format("\"%s\"", user.getStatus());
            holder.status.setText(status);
            holder.birthDate.setText(user.getBirthDate());
            holder.interests.setText(user.getInterests().trim());
            holder.mobilePhone.setText(user.getMobilePhone());
        } else if (viewHolder instanceof PostViewHolder && data.get(adapterPosition) instanceof Post) {
            PostViewHolder holder = (PostViewHolder) viewHolder;
            Post post = (Post) data.get(adapterPosition);
            String authorName = post.getAuthorFirstName() + " " + post.getAuthorLastName();
            holder.authorName.setText(authorName);
            if (!TextUtils.isEmpty(post.getText())) {
                holder.text.setText(post.getText());
                holder.text.setVisibility(View.VISIBLE);
            } else {
                holder.text.setVisibility(View.GONE);
            }
            holder.repostIndicator.setVisibility(post.isRepost()? View.VISIBLE : View.GONE);
            StringBuilder attachmentString = new StringBuilder();
            for (Map.Entry<String, Integer> pair : post.getAttachments().entrySet()) {
                attachmentString.append(String.format("%s : %d\n", pair.getKey(), pair.getValue()));
            }

            if (attachmentString.length() > 0) {
                holder.attachments.setText(attachmentString.toString().trim());
                holder.attachments.setVisibility(View.VISIBLE);
            } else {
                holder.attachments.setVisibility(View.GONE);
            }
            holder.likesCount.setText(String.valueOf(post.getLikesCount()));
            holder.repostsCount.setText(String.valueOf(post.getRepostsCount()));
            holder.date.setText(dateFormat.format(new Date(post.getDate() * 1000)));
            Picasso.with(context).load(post.getAuthorPhotoUrl()).into(holder.authorAvatar);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).equals(PROGRESS)) {
            return TYPE_FOOTER;
        } else if (data.get(position) instanceof User) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(VkInfo response) {
        data.clear();
        data.add(response.getUser());
        data.addAll(response.getPost());
        notifyDataSetChanged();
    }

    public void appendPosts(List<Post> post) {
        int startPosition = data.size();
        data.addAll(post);
        int count = post.size();

        notifyItemRangeInserted(startPosition, count);
    }

    public static int getColor(Context c, int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return c.getColor(resourceId);
        } else {
            return c.getResources().getColor(resourceId);
        }
    }

    private void tint(TextView textView, int resId, int color) {
        Drawable drawable = context.getDrawable(resId);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }


    static class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author_name)
        TextView authorName;
        @BindView(R.id.iv_post_avatar)
        ImageView authorAvatar;
        @BindView(R.id.tv_text)
        TextView text;
        @BindView(R.id.tv_post_date)
        TextView date;
        @BindView(R.id.tv_attachments)
        TextView attachments;
        @BindView(R.id.tv_repost)
        TextView repostIndicator;
        @BindView(R.id.tv_likes_count)
        TextView likesCount;
        @BindView(R.id.tv_reposts_count)
        TextView repostsCount;

        public PostViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    static class UserInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city)
        TextView cityView;
        @BindView(R.id.tv_about)
        TextView about;
        @BindView(R.id.tv_activities)
        TextView activities;
        @BindView(R.id.tv_status)
        TextView status;
        @BindView(R.id.tv_birth_date)
        TextView birthDate;
        @BindView(R.id.tv_interests)
        TextView interests;
        @BindView(R.id.tv_mobile_number)
        TextView mobilePhone;

        public UserInfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View view) {
            super(view);
        }
    }

}
