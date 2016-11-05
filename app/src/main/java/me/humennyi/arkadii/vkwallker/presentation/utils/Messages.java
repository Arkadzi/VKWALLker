package me.humennyi.arkadii.vkwallker.presentation.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;



/**
 * Created by sebastian on 21.06.16.
 */
@Singleton
public class Messages {

    private Context c;

    @Inject
    public Messages(Context c) {
        this.c = c;
    }

    public String getError(Throwable e) {
        String message = e.getMessage();
        return e.getMessage();
    }


    public String convertError(int messageId) {
        return c.getString(messageId);
    }
}
