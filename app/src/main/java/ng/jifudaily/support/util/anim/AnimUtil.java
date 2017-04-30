package ng.jifudaily.support.util.anim;

import android.view.View;

/**
 * Created by Ng on 2017/4/24.
 */

public final class AnimUtil {

    public static final int DEFAULT_DURATION = 350;
    public static final int DEFAULT_DURATION_HALF = DEFAULT_DURATION / 2;
    public static final int DEFAULT_DURATION_QRT = DEFAULT_DURATION / 4;
    public static final int DEFAULT_DURATION_DUO = DEFAULT_DURATION * 2;
    public static final int DEFAULT_DURATION_TRI = DEFAULT_DURATION * 3;

    public static <E extends AnimArgs<E>> E with(Class<E> animArgs, View... views) {
        return AnimArgs.create(animArgs, views);
    }

    public static AnimArgs<?> with(View... views) {
        return AnimArgs.create(views);
    }
}
