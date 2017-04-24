package ng.jifudaily.support.util;

import android.animation.Animator;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ng.jifudaily.support.ioc.service.AnimService;

/**
 * Created by Ng on 2017/4/24.
 */

public class FadeInCallback implements Callback {

    private ImageView view;
    private int duration;
    private Animator.AnimatorListener listener;

    public final static int DEFAULT_DURATION = 350;

    private FadeInCallback(ImageView v, int duration, Animator.AnimatorListener listenr) {
        this.view = v;
        this.duration = duration;
        this.listener = listenr;
    }

    public static FadeInCallback createInstance(ImageView v, int duration, Animator.AnimatorListener listener) {
        return new FadeInCallback(v, duration, listener);
    }

    public static FadeInCallback createInstance(ImageView v, int duration) {
        return new FadeInCallback(v, duration, null);
    }

    public static FadeInCallback createInstance(ImageView v) {
        return FadeInCallback.createInstance(v, DEFAULT_DURATION, null);
    }

    @Override
    public void onSuccess() {
        AnimService.FadeIn(view, duration, listener);
        Picasso.with(view.getContext()).cancelRequest(view);
    }

    @Override
    public void onError() {

    }
}
