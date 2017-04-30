package ng.jifudaily.support.util.anim;

import android.view.View;

import java.util.LinkedHashMap;

/**
 * Created by Ng on 2017/4/27.
 */

public class ObjectAnimArgs extends AnimArgs<ObjectAnimArgs> {
    private int mIntValues[];
    private float mFloatValues[];
    private String mPropertyName;

    public ObjectAnimArgs(View... views) {
        super(views);
    }

    public ObjectAnimArgs(LinkedHashMap<Anims, AnimArgs> map, View... views) {
        super(map, views);
    }

    public ObjectAnimArgs intValues(int... mValues) {
        this.mIntValues = mValues;
        return this;
    }

    public ObjectAnimArgs property(String mPropertyName) {
        this.mPropertyName = mPropertyName;
        return this;
    }

    public float[] getFloatValues() {
        return mFloatValues;
    }

    public ObjectAnimArgs floatValues(float[] floatValues) {
        mFloatValues = floatValues;
        return this;
    }

    public int[] getIntValues() {
        return mIntValues;
    }

    public String getPropertyName() {
        return mPropertyName;
    }
}
