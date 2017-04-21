package ng.jifudaily.view.base.container;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import ng.jifudaily.support.container.ContainerView;

/**
 * Created by Ng on 2017/4/21.
 */

public class LinearContainer extends LinearLayout implements ContainerView {
    public LinearContainer(Context context) {
        super(context);
    }

    public LinearContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
