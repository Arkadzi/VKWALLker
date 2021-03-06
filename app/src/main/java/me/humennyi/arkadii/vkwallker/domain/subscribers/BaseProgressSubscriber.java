package me.humennyi.arkadii.vkwallker.domain.subscribers;


import android.util.Log;


public class BaseProgressSubscriber<T> extends BaseUseCaseSubscriber<T> {
    private ProgressSubscriberListener listener;

    public BaseProgressSubscriber(ProgressSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        if (listener != null)
            listener.onStartLoading();
    }

    @Override
    public void onCompleted() {
        Log.e("useCase", "onComplete");
        if (listener != null)
            listener.onCompleted();
        listener = null;
    }

    @Override
    public void onError(Throwable e) {
        Log.e("useCase", "onError " + e);
        if (listener != null)
            listener.onError(e);
        listener = null;
    }

    @Override
    public void onNext(T response) {
        Log.e("useCase", "onNext " + response);
    }

    public interface ProgressSubscriberListener {
        void onError(Throwable t);

        void onCompleted();

        void onStartLoading();
    }
}