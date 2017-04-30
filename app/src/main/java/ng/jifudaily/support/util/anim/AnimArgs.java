package ng.jifudaily.support.util.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import io.reactivex.disposables.Disposable;
import ng.jifudaily.support.util.Action;

/**
 * Created by Ng on 2017/4/27.
 */

public class AnimArgs<T extends AnimArgs<T>> implements Disposable {

    private int mDuration = AnimUtil.DEFAULT_DURATION;
    private int mDelay = 0;
    private Interpolator mInterpolator;
    private Animator.AnimatorListener mListener;
    private Action<Animator> mStart;
    private Action<Animator> mEnd;
    private boolean mInitedListeners = false;
    private boolean mInitedInterpolator = false;
    private LinkedHashMap<Anims, AnimArgs> mAnimMap;
    private boolean mAutoCancel = true;
    private View[] mViews;


    AnimArgs(View[] views) {
        mViews = views;
        mAnimMap = new LinkedHashMap<>();
    }

    AnimArgs(LinkedHashMap<Anims, AnimArgs> map, View[] views) {
        mViews = views;
        mAnimMap = map;
    }

    public static AnimArgs<?> create(View... views) {
        return new AnimArgs(views);
    }

    public static <E extends AnimArgs<E>> E create(Class<E> clz, View... views) {
        return create(clz, null, views);
    }

    private static AnimArgs<?> create(@NonNull LinkedHashMap<Anims, AnimArgs> map, View... views) {
        return new AnimArgs(map, views);
    }

    private static <E extends AnimArgs<E>> E create(Class<E> clz, LinkedHashMap<Anims, AnimArgs> map, View... views) {
        try {
            E newInstance;
            Constructor<E> constructor;
            if (map == null) {
                constructor = clz.getConstructor(View[].class);
                constructor.setAccessible(true);

                newInstance = constructor.newInstance((Object) views);
            } else {
                constructor = clz.getConstructor(LinkedHashMap.class, View[].class);
                constructor.setAccessible(true);
                newInstance = constructor.newInstance(map, views);
            }
            return newInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T using(Anims anim) {
        getInnerAnimMap().put(anim, this);
        return getThis();
    }

    public AnimArgs<?> with(View... views) {
        return (AnimArgs) create(getInnerAnimMap(), views);
    }

    public <E extends AnimArgs<E>> E with(Class<E> clz, View... views) {
        return create(clz, getInnerAnimMap(), views);
    }

    View[] getViews() {
        return mViews;
    }

    public boolean isAutoCancel() {
        return mAutoCancel;
    }

    public T autoCancel(boolean autoCancel) {
        mAutoCancel = autoCancel;
        return getThis();
    }

    LinkedHashMap<Anims, AnimArgs> getAnimMap() {
        return mAnimMap;
    }

    int getDelay() {
        return mDelay;
    }

    public T delay(int delay) {
        this.mDelay = delay;
        return getThis();
    }

    int getDuration() {
        return mDuration;
    }

    public T duration(int duration) {
        this.mDuration = duration;
        return getThis();
    }

    Interpolator getInterpolator() {
        if (!mInitedInterpolator) {
            return new DecelerateInterpolator();
        }
        return mInterpolator;
    }

    boolean isInterpolatorInited() {
        return mInitedInterpolator;
    }

    public T interpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
        mInitedInterpolator = true;
        return getThis();
    }

    public T onStart(Action<Animator> start) {
        this.mStart = start;
        mInitedListeners = true;
        return getThis();
    }

    public T onEnd(Action<Animator> end) {
        this.mEnd = end;
        mInitedListeners = true;
        return getThis();
    }

    public T listener(Animator.AnimatorListener adapter) {
        this.mListener = adapter;
        mInitedListeners = true;
        return getThis();
    }

    boolean isListenerInited() {
        return mInitedListeners;
    }

    Animator.AnimatorListener getListener() {
        if (mListener == null && (mStart != null || mEnd != null)) {
            mListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (mStart != null) {
                        mStart.onCall(animation);
                        mStart = null;
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mEnd != null) {
                        mEnd.onCall(animation);
                        mEnd = null;
                    }
                    animation.removeListener(this);
                }
            };
        }
        return mListener;
    }


    public AnimExecutor start() {
        return start(true);
    }

    public AnimExecutor start(boolean playTogether) {
        return executor().start(playTogether);
    }

    public AnimExecutor executor() {
        return AnimExecutor.create(this);
    }

    private T getThis() {
        return (T) this;
    }

    private LinkedHashMap<Anims, AnimArgs> getInnerAnimMap() {
        if (mAnimMap == null) {
            mAnimMap = new LinkedHashMap<>();
        }
        return mAnimMap;
    }

    @Override
    public void dispose() {
        for (AnimArgs arg : mAnimMap.values()) {
            arg.mViews = null;
            arg.mInterpolator = null;
            arg.mListener = null;
//            arg.mStart = null;
//            arg.mEnd = null;
            if (arg != this) {
                arg.mAnimMap = null;
            }
        }
        mAnimMap.clear();
        mAnimMap = null;
    }

    @Override
    public boolean isDisposed() {
        return false;
    }
}
