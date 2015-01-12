package net.swapix.swapix;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class ActivityLogin extends ActionBarActivity {

	private final String TAG = ActivityLogin.class.getCanonicalName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
}
