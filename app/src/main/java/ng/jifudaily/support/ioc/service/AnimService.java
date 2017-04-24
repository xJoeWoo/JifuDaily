package ng.jifudaily.support.ioc.service;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Animation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Ng on 2017/4/24.
 */

public final class AnimService {

    public static final int DEFAULT_DURATION = 300;

    public static void FadeIn(View v, int duration, Animator.AnimatorListener listener) {
        Anim(v, duration, listener, Techniques.FadeIn);
    }


    public static void FadeOut(View v, int duration, Animator.AnimatorListener listener) {
        Anim(v, duration, listener, Techniques.FadeOut);

    }

    private static void Anim(View v, int duration, Animator.AnimatorListener listener, Techniques techniques) {
        YoYo.AnimationComposer c = YoYo.with(techniques)
                .duration(duration);
        if (listener != null) {
            c.withListener(listener);
        }
        c.playOn(v);
    }


}
