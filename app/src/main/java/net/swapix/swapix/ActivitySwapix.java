package net.swapix.swapix;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by caseystark on 1/10/15.
 */
public class ActivitySwapix extends ActionBarActivity {

    private final String TAG = ActivitySwapix.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swapix);
    }
}
