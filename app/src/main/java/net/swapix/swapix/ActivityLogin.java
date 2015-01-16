package net.swapix.swapix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import net.swapix.oauth.Constants;
import net.swapix.oauth.OAuthAccessTokenActivity;
import net.swapix.oauth.Oauth2Params;

public class ActivityLogin extends ActionBarActivity {

	private final String TAG = ActivityLogin.class.getCanonicalName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);

        startOauthFlow(Oauth2Params.SWAPIX_OAUTH);
//		setContentView(R.layout.activity_login);
	}

    private void startOauthFlow(Oauth2Params oauth2Params) {
        Constants.OAUTH2PARAMS = oauth2Params;
        startActivity(new Intent().setClass(this.getApplicationContext(), OAuthAccessTokenActivity.class));
    }
}
