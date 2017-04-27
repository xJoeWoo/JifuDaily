package ng.jifudaily.support.util;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ng.jifudaily.support.util.anim.AnimUtil;

/**
 * Created by Ng on 2017/4/24.
 */

public class FadeInCallback implements Callback {

    private ImageView view;
    private int duration;
    private AnimUtil.AnimArgs listener;


    private FadeInCallback(ImageView v, int duration, AnimUtil.AnimArgs listener) {
        this.view = v;
        this.duration = duration;
        this.listener = listener;
    }

    public static FadeInCallback createInstance(ImageView v, int duration, AnimUtil.AnimArgs listener) {
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
        AnimUtil.fadeIn(view, duration, listener);
        Picasso.with(view.getContext()).cancelRequest(view);
    }

    @Override
    public void onError() {

    }
}
