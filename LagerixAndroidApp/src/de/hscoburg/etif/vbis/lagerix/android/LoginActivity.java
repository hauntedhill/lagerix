package de.hscoburg.etif.vbis.lagerix.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;

/**
 * Activity which displays a login screen to the user
 * @author Felix Lisczyk
 * 
 */
public class LoginActivity extends Activity {

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	//Result of login call
	String loginResult = "";
	
	//IP address from settings
	String baseURL;

	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		PreferenceManager.setDefaultValues(this, R.layout.activity_settings, false);

		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}
	
	/**
	 * Gets called every time the activity appears on screen
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));
	}

	/**
	 * Initializer method for the action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login_actions, menu);
		return true;
	}
	
	/**
	 * Listener method for the action bar buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent intent = new Intent(this, SettingsActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner and perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			login();
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	/**
	 * Performs an actual login attempt using REST requests.
	 */
	public void login() {
		
		//Hide the keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
		
		// Create parameters for the REST request
		RequestParams restParams = new RequestParams();
		restParams.put("email", mEmail);
		restParams.put("password", mPassword);
		
		// Show the progress spinner
		showProgress(true);

		// This first REST request checks if the supplied credentials are valid.
		// It is required because the actual login request doesn't return a proper status.
		Log.d("login() REST-Request", "Sending REST request to: "+baseURL+getString(R.string.restURI_login1));
		LagerixRestClient.post(baseURL+getString(R.string.restURI_login1), restParams, new TextHttpResponseHandler() {

			// The first REST request was successful. The user provided valid credentials.
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
	                loginResult = responseBody;
	                Log.d("login(): First REST-Request", "Response: "+responseBody);
					Log.d("login(): First REST-Request", "Statuscode: "+statusCode);
	                
        			RequestParams loginParams = new RequestParams();
        			loginParams.put("j_username", mEmail);
        			loginParams.put("j_password", mPassword);
	        			
        			// The second REST request performs the actual login.
        			LagerixRestClient.post(baseURL+getString(R.string.restURI_login2), loginParams, new TextHttpResponseHandler() {
        				// The second REST request was successful. The first REST request already checked the user credentials, so we can forward to the main application.
        				@Override
        				public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
        	                Log.d("login(): Second REST-Request", "Response: "+responseBody);
        					Log.d("login(): Second REST-Request", "Statuscode: "+statusCode);
        					showProgress(false);
        					Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        			    	startActivity(intent);
        			    	finish();
        				}
        				// The second REST request failed. This should never happen but is handled nevertheless as a precaution.
        				@Override
        				public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable e) {
        					Log.e("login(): Second REST-Request", "Error: "+responseBody);
        					Log.e("login(): Second REST-Request", "Statuscode: "+statusCode);
        					
        					// Hide the progess indicator
        					showProgress(false);
        					
        					//Show an error message depending on the status code
        					if(statusCode == 401)
        						Toast.makeText(getApplicationContext(), getString(R.string.error_incorrect_credentials), Toast.LENGTH_LONG).show();
        					else if(statusCode == 403)
        						Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();	
        					else
        						Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();	
        				}
        			});
			}
			
			//The first REST request failed due to a server error or invalid credentials.
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
				Log.e("login(): First REST-Request", "Error: "+responseBody);
				Log.e("login(): First REST-Request", "Statuscode: "+statusCode);
				
				// Hide the progess indicator
				showProgress(false);
				
				//Show an error message depending on the status code
				if(statusCode == 401)
					Toast.makeText(getApplicationContext(), getString(R.string.error_incorrect_credentials), Toast.LENGTH_LONG).show();
				else if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();	
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();

			}
		});		
	}

}
