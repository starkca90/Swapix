package net.swapix.oauth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.swapix.swapix.ActivitySwapix;

/**
 * Execute the OAuthRequestTokenTask to retrieve the request, and authorize the request.
 * After the request is authorized by the user, the callback URL will be intercepted here.
 */
@SuppressLint("SetJavaScriptEnabled")
public class OAuthAccessTokenActivity extends ActionBarActivity {

    boolean handled = false;
    private SharedPreferences prefs;
    private OAuth2Helper oAuth2Helper;
    private WebView webview;
    private boolean hasLoggedIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.TAG, "Starting task to retrieve request token.");
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        oAuth2Helper = new OAuth2Helper(this.prefs);
        webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVisibility(View.VISIBLE);
        setContentView(webview);

        String authorizationUrl = oAuth2Helper.getAuthorizationUrl();
        Log.i(Constants.TAG, "Using authorizationUrl = " + authorizationUrl);

        handled = false;

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap bitmap) {
                Log.d(Constants.TAG, "onPageStarted : " + url + " handled = " + handled);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                Log.d(Constants.TAG, "onPageFinished : " + url + " handled = " + handled);

                if (url.startsWith(Constants.OAUTH2PARAMS.getRederictUri())) {
                    webview.setVisibility(View.INVISIBLE);

                    if (!handled) {
                        new ProcessToken(url, oAuth2Helper).execute();
                    }
                } else {
                    webview.setVisibility(View.VISIBLE);
                }
            }

        });

        webview.loadUrl(authorizationUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Constants.TAG, "onResume called with " + hasLoggedIn);
        if (hasLoggedIn) {
            finish();
        }
    }


    private class ProcessToken extends AsyncTask<Uri, Void, Void> {

        String url;
        boolean startActivity = false;


        public ProcessToken(String url, OAuth2Helper oAuth2Helper) {
            this.url = url;
        }

        @Override
        protected Void doInBackground(Uri... params) {


            if (url.startsWith(Constants.OAUTH2PARAMS.getRederictUri())) {
                Log.i(Constants.TAG, "Redirect URL found" + url);
                handled = true;
                try {
                    if (url.indexOf("code=") != -1) {
                        String authorizationCode = extractCodeFromUrl(url);

                        Log.i(Constants.TAG, "Found code = " + authorizationCode);

                        oAuth2Helper.retrieveAndStoreAccessToken(authorizationCode);
                        startActivity = true;
                        hasLoggedIn = true;

                    } else if (url.indexOf("error=") != -1) {
                        startActivity = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.i(Constants.TAG, "Not doing anything for url " + url);
            }
            return null;
        }

        private String extractCodeFromUrl(String url) throws Exception {
            String encodedCode = url.substring(Constants.OAUTH2PARAMS.getRederictUri().length() + 6, url.length() - 7);
            return encodedCode;
            //return URLDecoder.decode(encodedCode,"UTF-8");
        }

        @Override
        protected void onPreExecute() {

        }

        /**
         * When we're done and we've retrieved either a valid token or an error from the server,
         * we'll return to our original activity
         */
        @Override
        protected void onPostExecute(Void result) {
            if (startActivity) {
                Log.i(Constants.TAG, " ++++++++++++ Starting mainscreen again");
                startActivity(new Intent(OAuthAccessTokenActivity.this, ActivitySwapix.class));
                finish();
            }

        }

    }
}
