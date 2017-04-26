package ng.jifudaily.support.ioc.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import ng.jifudaily.support.util.Action;

/**
 * Created by Ng on 2017/4/24.
 */

public final class AnimUtil {

    public static final int DEFAULT_DURATION = 350;

    public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    public static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    public static void object(@NonNull View v, String propertyName, ObjectArgs args, int... ints) {

        if (args == null) {
            args = new ObjectArgs();
        }

        ObjectAnimator anim = ObjectAnimator.ofInt(v, propertyName, ints);
        anim.setDuration(args.getDuration());
        anim.setInterpolator(args.getInterpolator());
        anim.addListener(args.getListener());
        anim.start();
    }

    public static Animator circularReveal(@NonNull View v, boolean show, int duration, ObjectArgs args) {
        Rect r = new Rect();
        v.getLocalVisibleRect(r);
        return circularReveal(v, v.getWidth() / 2 + r.left, v.getHeight() / 2 + r.top, show, duration, args);
    }

    public static Animator circularReveal(@NonNull View v, int x, int y, boolean show, int duration, @Nullable ObjectArgs args) {

        Rect r = new Rect();
        v.getGlobalVisibleRect(r);


        int maxY = Math.max(Math.abs(r.bottom - y), Math.abs(y - r.top));
        int maxX = Math.max(Math.abs(r.left - x), Math.abs(r.right - x));

        int radius = (int) Math.sqrt(maxY * maxY + maxX * maxX);

//        int h = v.getHeight();
//        int w = v.getWidth();
//        int max = Math.max(h, w);
        float from = show ? 0 : radius;
        float to = show ? radius : 0;

        x = x - r.left;
        y = y - r.top;

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(v, x, y, from, to);

        if (args == null) {
            args = new ObjectArgs().interpolator(ACCELERATE_INTERPOLATOR);
        } else if (!args.isInterpolatorInited()) {
            args = args.interpolator(ACCELERATE_INTERPOLATOR);
        }
        if (args.isListenerInited()) {
            circularReveal.addListener(args.getListener());
        }
//        circularReveal.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                v.setVisibility(show ? View.VISIBLE : View.GONE);
//                circularReveal.removeListener(this);
//            }
//        });
        circularReveal.setDuration(duration);
        circularReveal.start();
        return circularReveal;
    }

    public static void fadeIn(@NonNull View v, int duration, ObjectArgs args) {
        anim(v, duration, args, Techniques.FadeIn);
    }

    public static void fadeOut(@NonNull View v, int duration, ObjectArgs args) {
        anim(v, duration, args, Techniques.FadeOut);
    }

    public static void zoomOut(@NonNull View v, int duration, ObjectArgs args) {
        anim(v, duration, args, Techniques.ZoomOut);
    }

    private static void anim(@NonNull View v, int duration, ObjectArgs args, Techniques techniques) {
        if (args == null) {
            args = new ObjectArgs();
        }
        YoYo.AnimationComposer c = YoYo.with(techniques)
                .duration(duration)
                .withListener(args.getListener())
                .interpolate(args.getInterpolator())
                .delay(args.getDelay());
        c.playOn(v);
    }

    public static class ObjectArgs {


        private int duration = DEFAULT_DURATION;
        private Interpolator interpolator;

        private int delay;
        private AnimatorListenerAdapter listener;
        private Action<Animator> start;
        private Action<Animator> end;
        private boolean initedListeners = false;
        private boolean initedInterpolator = false;

        public int getDelay() {
            return delay;
        }

        public ObjectArgs delay(int delay) {
            this.delay = delay;
            return this;
        }

        public int getDuration() {
            return duration;
        }

        public ObjectArgs duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Interpolator getInterpolator() {
            if (interpolator == null) {
                return DECELERATE_INTERPOLATOR;
            }
            return interpolator;
        }

        public boolean isInterpolatorInited() {
            return initedInterpolator;
        }

        public ObjectArgs interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            initedInterpolator = true;
            return this;
        }

        public ObjectArgs start(Action<Animator> start) {
            this.start = start;
            initedListeners = true;
            return this;
        }

        public ObjectArgs end(Action<Animator> end) {
            this.end = end;
            initedListeners = true;
            return this;
        }

        public boolean isListenerInited() {
            return initedListeners;
        }


        public AnimatorListenerAdapter getListener() {
            if (listener == null) {
                listener = new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        if (start != null) {
                            start.onCall(animation);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (end != null) {
                            end.onCall(animation);
                        }
                        animation.removeListener(this);
                    }
                };
            }
            return listener;
        }
    }


}
