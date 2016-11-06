package me.humennyi.arkadii.vkwallker.domain.usecase;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Arkadiy on 05.06.2016.
 */
public abstract class UseCase<T> {
    private Subscription subscription = Subscriptions.empty();
    private Observable<T> observable;

    public void execute(Subscriber<T> subscriber) {
        Log.e("subscription", "subscribe " + getClass() + " " + observable);
        if (observable == null)
            observable = getUseCaseObservable()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache()
                    .doOnError((t) -> observable = null)
                    .doOnCompleted(() -> observable = null);
        subscription = observable.subscribe(subscriber);
    }

    protected abstract Observable<T> getUseCaseObservable();

    public boolean isWorking() {
        return observable != null;
    }

    public void unsubscribe() {
        Log.e("subscription", "unsubscribe " + getClass());
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected void stopExecution() {
        unsubscribe();
        observable = null;
    }
}
