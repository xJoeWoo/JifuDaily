package ng.jifudaily.support.util;

import android.util.DisplayMetrics;

/**
 * Created by Ng on 2017/4/26.
 */

public class DpUtil {

    private static DpUtil instance;
    private DisplayMetrics metrics;

    private DpUtil(DisplayMetrics metrics) {
        this.metrics = metrics;
    }

    public static void init(DisplayMetrics metrics) {
        instance = new DpUtil(metrics);
    }


    public static int pxToDp(int px) {
        return (int) ((px / instance.metrics.density) + 0.5);
    }

    public static int dpToPx(int dp) {
        return (int) ((dp * instance.metrics.density) + 0.5);
    }

}
