package ng.jifudaily.support.util.anim;

import android.view.View;

import java.util.LinkedHashMap;

/**
 * Created by Ng on 2017/4/27.
 */

public class CircularRevealAnimArgs extends AnimArgs<CircularRevealAnimArgs> {
    private int mX;
    private int mY;

    private boolean mRelative = true;

    public CircularRevealAnimArgs(View... views) {
        super(views);
    }

    public CircularRevealAnimArgs(LinkedHashMap<Anims, AnimArgs> map, View... views) {
        super(map, views);
    }

    public boolean isRelative() {
        return mRelative;
    }

    public CircularRevealAnimArgs relative(boolean relative) {
        mRelative = relative;
        return this;
    }

    public int getX() {
        return mX;
    }

    public CircularRevealAnimArgs x(int x) {
        mX = x;
        return this;
    }

    public int getY() {
        return mY;
    }

    public CircularRevealAnimArgs y(int y) {
        mY = y;
        return this;
    }
}
