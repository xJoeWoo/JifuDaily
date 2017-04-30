package ng.jifudaily.support.util;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by Ng on 2017/4/26.
 */

public class UnitTool {

    private DisplayMetrics metrics;

    public UnitTool(Application application) {
        metrics = application.getResources().getDisplayMetrics();
    }

    public int pxToDp(int px) {
        return (int) ((px / metrics.density) + 0.5);
    }

    public int dpToPx(int dp) {
        return (int) ((dp * metrics.density) + 0.5);
    }

}
