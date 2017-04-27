package ng.jifudaily.support.util.anim;

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

//TODO refactor this util to service and remove dependency to AndroidViewAnimations
public final class AnimUtil {

    public static final int DEFAULT_DURATION = 350;

    public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    public static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();


    public static Animator object(@NonNull View v, String propertyName, AnimArgs args, int... ints) {

        if (args == null) {
            args = new AnimArgs();
        }

        ObjectAnimator anim = ObjectAnimator.ofInt(v, propertyName, ints);
        anim.setDuration(args.getDuration());
        anim.setInterpolator(args.getInterpolator());
        anim.addListener(args.getListener());
        if (args.isStartImmediately()) {
            anim.start();
        }
        return anim;
    }

    public static Animator circularReveal(@NonNull View v, boolean show, int duration, AnimArgs args) {
        Rect r = new Rect();
        v.getLocalVisibleRect(r);
        return circularReveal(v, v.getWidth() / 2 + r.left, v.getHeight() / 2 + r.top, show, duration, args);
    }

    public static Animator circularReveal(@NonNull View v, int x, int y, boolean show, int duration, @Nullable AnimArgs args) {

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
            args = new AnimArgs().interpolator(ACCELERATE_INTERPOLATOR);
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
        if (args.isStartImmediately()) {
            circularReveal.start();
        }
        return circularReveal;
    }

    public static void fadeIn(View v, int duration, AnimArgs args) {
        anim(v, duration, args, Techniques.FadeIn);
    }

    public static void fadeOut(View v, int duration, AnimArgs args) {
        anim(v, duration, args, Techniques.FadeOut);
    }

    public static void zoomOut(View v, int duration, AnimArgs args) {
        anim(v, duration, args, Techniques.ZoomOut);
    }

    private static void anim(View v, int duration, AnimArgs args, Techniques techniques) {
        if (v == null) {
            return;
        }
        if (args == null) {
            args = new AnimArgs();
        }


        YoYo.AnimationComposer c = YoYo.with(techniques)
                .duration(duration)
                .withListener(args.getListener())
                .interpolate(args.getInterpolator())
                .delay(args.getDelay());
        c.playOn(v);
    }

    //TODO move outside
    public static class AnimArgs {

        private int duration = DEFAULT_DURATION;
        private Interpolator interpolator;

        private int delay;
        private AnimatorListenerAdapter listener;
        private Action<Animator> start;
        private Action<Animator> end;
        private boolean initedListeners = false;
        private boolean initedInterpolator = false;
        private boolean isStartImmediately = true;

        private AnimArgs() {
        }

        public static AnimArgs create() {
            return new AnimArgs();
        }

        public boolean isStartImmediately() {
            return isStartImmediately;
        }

        public AnimArgs setStartImmediately(boolean startImmediately) {
            isStartImmediately = startImmediately;
            return this;
        }

        public int getDelay() {
            return delay;
        }

        public AnimArgs delay(int delay) {
            this.delay = delay;
            return this;
        }

        public int getDuration() {
            return duration;
        }

        public AnimArgs duration(int duration) {
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

        public AnimArgs interpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            initedInterpolator = true;
            return this;
        }

        public AnimArgs start(Action<Animator> start) {
            this.start = start;
            initedListeners = true;
            return this;
        }

        public AnimArgs end(Action<Animator> end) {
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
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                    }

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
