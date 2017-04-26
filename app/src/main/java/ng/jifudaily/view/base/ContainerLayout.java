package ng.jifudaily.view.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import ng.jifudaily.R;

/**
 * Created by Ng on 2017/4/24.
 */

public class ContainerLayout extends FrameLayout {
    public ContainerLayout(@NonNull Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundResource(R.color.colorPrimaryDark);
    }

    public ContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundResource(R.color.colorPrimaryDark);
    }
}
