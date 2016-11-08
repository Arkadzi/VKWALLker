package me.humennyi.arkadii.vkwallker.data.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.humennyi.arkadii.vkwallker.data.entities.AttachmentEntity;
import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;
import me.humennyi.arkadii.vkwallker.domain.Post;

/**
 * Created by arkadii on 11/5/16.
 */
public class PostMapper implements Mapper<PostEntity, Post> {

    @Override
    public Post transform(PostEntity obj) throws RuntimeException {
        Post post = new Post();
        post.setAuthorFirstName(obj.getFromFirstName());
        post.setAuthorLastName(obj.getFromLastName());
        post.setAuthorPhotoUrl(obj.getFromPhoto());
        post.setDate(obj.getDate());
        post.setRepostsCount(obj.getReposts().getCount());
        post.setLikesCount(obj.getLikes().getCount());
        post.setText(obj.getText());
        post.setRepost(obj.getCopyHistory() != null);
        post.setId(obj.getId());

        Map<String, Integer> attachmentsCount = new HashMap<>();
        if (obj.getAttachments() != null) {
            List<AttachmentEntity> attachments = obj.getAttachments();
            for (AttachmentEntity attachment : attachments) {
                Integer count = attachmentsCount.get(attachment.getType());
                if (count == null) count = 1;
                else count = count + 1;
                attachmentsCount.put(attachment.getType(), count);
            }
        }
        post.setAttachments(attachmentsCount);
        return post;
    }
}
