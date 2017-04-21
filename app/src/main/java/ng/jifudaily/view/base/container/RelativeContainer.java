package ng.jifudaily.view.base.container;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import ng.jifudaily.support.container.ContainerView;

/**
 * Created by Ng on 2017/4/21.
 */

public class RelativeContainer extends RelativeLayout implements ContainerView {
    public RelativeContainer(Context context) {
        super(context);
    }

    public RelativeContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
