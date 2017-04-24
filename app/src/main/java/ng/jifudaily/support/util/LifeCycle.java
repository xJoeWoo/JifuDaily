package ng.jifudaily.support.util;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Ng on 2017/4/21.
 */

public interface LifeCycle {

    void onNewIntent(Intent intent);

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onRestart();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    boolean onBackPressed();
}
