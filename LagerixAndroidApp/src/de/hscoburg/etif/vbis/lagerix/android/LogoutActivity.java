package de.hscoburg.etif.vbis.lagerix.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * Activity which logs the user out of the application and sends him back to the login screen
 * @author Felix Lisczyk
 *
 */
public class LogoutActivity extends Activity {

	private View mLogoutStatusView;

	//Result of logout call
	String logoutResult = "";

	//IP address from settings
	String baseURL;

	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));

		mLogoutStatusView = findViewById(R.id.logout_status);

		logout();
	}

	/**
	 * Sends a REST request to perform a logout
	 */
	public void logout() {
		showProgress(true);

		LagerixRestClient.get(baseURL+getString(R.string.restURI_logout), new TextHttpResponseHandler() {

			public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
				Log.d("logout(): REST-Request", "Response: "+responseBody);
				Log.d("logout(): REST-Request", "Statuscode: "+statusCode);
				logoutResult = responseBody;

				// Logout was successful, forward the user to the login activity
				if(logoutResult.equals("SUCCESS")) {
					showProgress(false);
					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(intent);
					finish();
				}

				// Logout failed, send the user back to the scan activity
				else {
					Log.e("LogoutActivity", "Error logging out");
					showProgress(false);
					Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
					startActivity(intent);
					finish();

				}
			}

			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
				Log.e("logout(): REST-Request", "Error: "+responseBody);
				Log.e("logout(): REST-Request", "Statuscode: "+statusCode);
				showProgress(false);
				
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), R.string.status_not_authorized, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), R.string.status_communication_error, Toast.LENGTH_LONG).show();

			}
		});		


	}

	/**
	 * Shows the progress UI and hides the logout form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLogoutStatusView.setVisibility(View.VISIBLE);
			mLogoutStatusView.animate().setDuration(shortAnimTime)
			.alpha(show ? 1 : 0)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLogoutStatusView.setVisibility(show ? View.VISIBLE
							: View.GONE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLogoutStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}

}
