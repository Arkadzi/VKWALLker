package me.humennyi.arkadii.vkwallker.data.mapper;

/**
 * Created by arkadii on 11/5/16.
 */

public interface Mapper<A, B> {
    B transform(A obj) throws RuntimeException;
}
