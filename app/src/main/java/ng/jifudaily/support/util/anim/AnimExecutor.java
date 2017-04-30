package ng.jifudaily.support.util.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ng.jifudaily.support.util.Action;

/**
 * Created by Ng on 2017/4/27.
 */

public final class AnimExecutor {

    private AnimArgs mArgs;
    private Action<Animator> mEnd;
    private Action<Animator> mStart;
    private Animator.AnimatorListener mListener;

    public AnimExecutor(AnimArgs args) {
        this.mArgs = args;
    }

    public static AnimExecutor create(AnimArgs args) {
        return new AnimExecutor(args);
    }

    private static Animator circularReveal(@NonNull View v, CircularRevealAnimArgs args, boolean in) {
        Rect r = new Rect();
        v.getGlobalVisibleRect(r);

        int x = args.getX();
        int y = args.getY();

        if (!args.isRelative()) {
            x = x - r.left;
            y = y - r.top;
        }

        int maxY = Math.max(Math.abs(r.bottom - y), Math.abs(y - r.top));
        int maxX = Math.max(Math.abs(r.left - x), Math.abs(r.right - x));

        int radius = (int) Math.sqrt(maxY * maxY + maxX * maxX);

        float from = in ? 0 : radius;
        float to = in ? radius : 0;

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(v, x, y, from, to);
        if (!args.isInterpolatorInited()) {
            args = args.interpolator(new AccelerateInterpolator());
        }
        if (args.isListenerInited()) {
            circularReveal.addListener(args.getListener());
        }
        circularReveal.setDuration(args.getDuration());
        return circularReveal;
    }

    private static Animator fade(View v, AnimArgs args, boolean in) {
        return object(v, "alpha", args, in ? 0f : 1, in ? 1 : 0);
    }

    private static Animator[] zoom(View v, AnimArgs args, boolean in) {
        return new Animator[]{object(v, "alpha", args, in ? 0f : 1, in ? 1 : 0, in ? 1 : 0),
                // remember the setAlpha is float type ----- ^
                object(v, "scaleX", args, in ? 0 : 1, 0.3f, in ? 1 : 0),
                object(v, "scaleY", args, in ? 0 : 1, 0.3f, in ? 1 : 0)};
    }

    private static ObjectAnimator object(View v, String propertyName, AnimArgs args, float... values) {
        ObjectAnimator objectAnimator = object(v, propertyName, args);
        objectAnimator.setFloatValues(values);
        return objectAnimator;
    }

    private static ObjectAnimator object(View v, String propertyName, AnimArgs args, int... values) {
        ObjectAnimator objectAnimator = object(v, propertyName, args);
        objectAnimator.setIntValues(values);
        return objectAnimator;
    }

    private static ObjectAnimator object(View v, String propertyName, AnimArgs args) {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        objectAnimator.setPropertyName(propertyName);
        objectAnimator.setTarget(v);
        objectAnimator.setStartDelay(args.getDelay());
        objectAnimator.setDuration(args.getDuration());
        objectAnimator.setInterpolator(args.getInterpolator());
        if (args.getListener() != null) {
            objectAnimator.addListener(args.getListener());
        }
        return objectAnimator;
    }

    private static ObjectAnimator object(View v, ObjectAnimArgs args) {
        if (args.getFloatValues() != null) {
            return object(v, args.getPropertyName(), args, args.getFloatValues());
        }
        return object(v, args.getPropertyName(), args, args.getIntValues());
    }

    // animation codes are from https://github.com/daimajia/AndroidViewAnimations project

    public AnimExecutor start() {
        return start(true);
    }

    public AnimExecutor start(boolean playTogether) {

        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>(3);

        if (getListener() != null) {
            animatorSet.addListener(getListener());
        }

        for (Map.Entry<Anims, AnimArgs> entry : (Iterable<Map.Entry<Anims, AnimArgs>>) mArgs.getAnimMap().entrySet()) {
            if (entry.getKey() == Anims.Invalid) {
                continue;
            }
            for (View v : entry.getValue().getViews()) {
                switch (entry.getKey()) {
                    case FadeIn:
                        animators.add(fade(v, entry.getValue(), true));
                        break;
                    case FadeOut:
                        animators.add(fade(v, entry.getValue(), false));
                        break;
                    case ZoomIn:
                        animators.addAll(Arrays.asList(zoom(v, entry.getValue(), true)));
                        break;
                    case ZoomOut:
                        animators.addAll(Arrays.asList(zoom(v, entry.getValue(), false)));
                        break;
                    case CircularRevealIn:
                        animators.add(circularReveal(v, (CircularRevealAnimArgs) entry.getValue(), true));
                        break;
                    case CircularRevealOut:
                        animators.add(circularReveal(v, (CircularRevealAnimArgs) entry.getValue(), false));
                        break;
                    case Object:
                        animators.add(object(v, (ObjectAnimArgs) entry.getValue()));
                        break;
                }
            }
        }

        if (playTogether) {
            animatorSet.playTogether(animators);
        } else {
            animatorSet.playSequentially(animators);
        }
        animatorSet.start();

        mArgs.dispose();
        mArgs = null;
        mListener = null;
        return this;
    }

    public AnimExecutor globalListener(Animator.AnimatorListener listener) {
        mListener = listener;
        return this;
    }

    public AnimExecutor globalStart(Action<Animator> action) {
        mStart = action;
        return this;
    }

    public AnimExecutor globalEnd(Action<Animator> action) {
        mEnd = action;
        return this;
    }

    private Animator.AnimatorListener getListener() {
        if (mListener == null && mStart != null || mEnd != null) {
            mListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (mStart != null) {
                        mStart.onCall(animation);
                        //TODO test how to release pointer when multi animations
//                        mStart = null;
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mEnd != null) {
                        mEnd.onCall(animation);
//                        mEnd = null;
                    }
                }
            };
        }
        return mListener;
    }
}
