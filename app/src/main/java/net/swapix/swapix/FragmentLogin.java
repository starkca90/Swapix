package net.swapix.swapix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import net.swapix.oauth.Constants;
import net.swapix.oauth.OAuthAccessTokenActivity;
import net.swapix.oauth.Oauth2Params;

public class FragmentLogin extends Fragment {

    private final String TAG = FragmentLogin.class.getCanonicalName();
//    private final String URL = "http://picture.jessestark.com/apps/";

    //    private final String GRANT_TYPE = "password";
//    private final String USERNAME = "&username=";
//    private final String PASSWORD = "&password=";
//    private final String ERROR_MESSAGE = "error_description";
//    private final String ACCESS_TOKEN = "access_token";
//    private final String REFRESH_TOKEN = "refresh_token";
//    private String clientId = "ERROR";
//    private String clientSecret = "ERROR";

//    private EditText etUName;
//    private EditText etPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        startOauthFlow(Oauth2Params.SWAPIX_OAUTH);
    }

    /**
     * Starts the activity that takes care of the OAuth2 flow
     *
     * @param oauth2Params
     */
    private void startOauthFlow(Oauth2Params oauth2Params) {
        Constants.OAUTH2PARAMS = oauth2Params;
        startActivity(new Intent().setClass(this.getActivity(), OAuthAccessTokenActivity.class));
    }

/*
        clientId = ApplicationController.getInstance().getClientID();
		clientSecret = ApplicationController.getInstance().getClientSecret();



		etUName = (EditText) view.findViewById(R.id.etUName);
		etPass = (EditText) view.findViewById(R.id.etPass);

		Button btnLogin = (Button) view.findViewById(R.id.butLogin);
		chkRemember = (CheckBox) view.findViewById(R.id.chkRemember);

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uName = etUName.getText().toString();
				String pass = etPass.getText().toString();

				String urlArguments = "access_token" + "?grant_type="
						+ GRANT_TYPE + "&client_id=" + clientId + "&client_secret="
						+ clientSecret;
				
				String fullURL = URL + urlArguments + USERNAME + uName
						+ PASSWORD + pass;

				JsonObjectRequest req;
				req = new JsonObjectRequest(Method.POST, fullURL, null,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								try {
									ApplicationController.getInstance().setAccessToken(response.getString(ACCESS_TOKEN));
									ApplicationController.getInstance().setRefreshToken(response.getString(REFRESH_TOKEN));
									
									listener.onLoginSuccess();
								} catch (JSONException e) {
									// token does not exist, something broke
									Toast.makeText(getActivity(),
											"App Update Required To Connect",
											Toast.LENGTH_SHORT).show();
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {

								VolleyLog.e("Error: "
										+ Integer
												.toString(error.networkResponse.statusCode));
								String errorMessage = "ERROR NO MESSAGE";

								try {
									byte[] data = error.networkResponse.data;
									String stringData = new String(data,
											"UTF-8");

									JSONObject response = new JSONObject(
											stringData);
									errorMessage = response
											.getString(ERROR_MESSAGE);
								} catch (JSONException e) {
									Toast.makeText(getActivity(),
											"App Update Required To Connect",
											Toast.LENGTH_SHORT).show();
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								switch (error.networkResponse.statusCode) {
								case 400:
									Toast.makeText(getActivity(), errorMessage,
											Toast.LENGTH_SHORT).show();
									break;
								default:
									// TODO Something???
									break;
								}
							}

						});
				// add the request object to the queue to be executed
				ApplicationController.getInstance().addToRequestQueue(req);
			}

		});*/
}
