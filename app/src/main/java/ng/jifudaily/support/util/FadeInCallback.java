package ng.jifudaily.support.util;

import android.animation.Animator;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ng.jifudaily.support.util.anim.AnimUtil;
import ng.jifudaily.support.util.anim.Anims;

/**
 * Created by Ng on 2017/4/24.
 */

public class FadeInCallback implements Callback {

    private ImageView view;
    private int duration;
    private Animator.AnimatorListener listener;

    private FadeInCallback(ImageView v, int duration, Animator.AnimatorListener listener) {
        this.view = v;
        this.duration = duration;
        this.listener = listener;
    }

    public static FadeInCallback createInstance(ImageView v, int duration, Animator.AnimatorListener listener) {
        return new FadeInCallback(v, duration, listener);
    }

    public static FadeInCallback createInstance(ImageView v, int duration) {
        return new FadeInCallback(v, duration, null);
    }

    public static FadeInCallback createInstance(ImageView v) {
        return FadeInCallback.createInstance(v, AnimUtil.DEFAULT_DURATION, null);
    }

    @Override
    public void onSuccess() {
        AnimUtil.with(view).using(Anims.FadeIn).duration(duration).listener(listener).start();
        Picasso.with(view.getContext()).cancelRequest(view);
    }

    @Override
    public void onError() {
        Picasso.with(view.getContext()).cancelRequest(view);
    }
}
