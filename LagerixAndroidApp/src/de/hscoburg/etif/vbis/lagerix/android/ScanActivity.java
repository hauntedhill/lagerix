package de.hscoburg.etif.vbis.lagerix.android;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;

/**
 * Activity which displays the booking interface to the user
 * @author Felix Lisczyk
 *
 */
public class ScanActivity extends Activity {

	//UI elements
	TextView articleResultView;
	TextView storageLocationResultView;
	RadioButton isBookedIn;
	ProgressBar spinner;

	//IP address from settings
	String baseURL;

	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		articleResultView = (TextView) findViewById(R.id.label_articleIDResult);
		storageLocationResultView = (TextView) findViewById(R.id.label_storageIDResult);

		isBookedIn = (RadioButton) findViewById(R.id.radio_bookedIn);
		
		spinner = (ProgressBar) findViewById(R.id.activityIndicator_restRequest);
		spinner.setVisibility(View.INVISIBLE);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));
	}

	/**
	 * Initializer method for the action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_scan_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Listener method for the action bar buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {

		// Search button has been pressed
		case R.id.action_search:
			Intent searchIntent = new Intent(this, SearchFormActivity.class);
			startActivity(searchIntent);
			return true;
		// Storage overview button has been pressed
		case R.id.action_storage_overview:
			Intent overviewIntent = new Intent(this, StorageOverviewActivity.class);
			startActivity(overviewIntent);
			return true;
		// Logout button has been pressed
		case R.id.action_logout:
			Intent logoutIntent = new Intent(this, LogoutActivity.class);
			startActivity(logoutIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putCharSequence("articleResultView", articleResultView.getText().toString());
		outState.putCharSequence("storageLocationResultView", storageLocationResultView.getText().toString());
		outState.putBoolean("isBookedIn", isBookedIn.isChecked());
	}
	
	protected void onRestoreInstanceState(Bundle savedState) {		
		super.onRestoreInstanceState(savedState);
		
		articleResultView.setText(savedState.getCharSequence("articleResultView"));
		storageLocationResultView.setText(savedState.getCharSequence("storageLocationResultView"));
		isBookedIn.setChecked(savedState.getBoolean("isBookedIn"));

	}

	/**
	 * Opens the barcode scanning activity
	 * Gets called from UI button "Scan barcode"
	 * @param view
	 */
	public void scanBarcode(View view) {

		try {

			Intent intent = new Intent(
					"de.hscoburg.etif.vbis.lagerix.android.barcode.SCAN");
			// Tell the barcode scanner to return immediately after the barcode has been scanned
			intent.putExtra("RESULT_DISPLAY_DURATION_MS", 0l);
			startActivityForResult(intent, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

		}
	}

	/**
	 * Callback method for the barcode scanner
	 * Gets called when a barcode was recognized
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
				String result = intent.getStringExtra("SCAN_RESULT");

				//Article barcodes start with the letter 'A', so we check the first character
				if(result.charAt(0) == 'A')
					articleResultView.setText(result.substring(1));

				//Storage barcodes start withe the letter 'S'
				else if(result.charAt(0) == 'S')
					storageLocationResultView.setText(result.substring(1));
			}
		}
	}

	/**
	 * Sends a REST request containing the barcode data and selected UI elements
	 * Gets called from UI button "Send data"
	 * @param view
	 */
	public void sendEntry(View view) {
		if(articleResultView.getText().length() != 0 && storageLocationResultView.getText().length() != 0) {

			// Create parameters for the REST request
			RequestParams params = new RequestParams();
			params.put("articleID", articleResultView.getText().toString());
			params.put("locationID", storageLocationResultView.getText().toString());
			if(isBookedIn.isChecked())
				params.put("isBookedIn", "true");
			else
				params.put("isBookedIn", "false");
			params.put("timestamp", ""+Calendar.getInstance().getTimeInMillis());
			
			spinner.setVisibility(View.VISIBLE);

			// Call the REST helper class and send the request
			LagerixRestClient.post(baseURL+getString(R.string.restURI_bookEntry), params, new TextHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
					Log.d("sendEntry(): REST-Request", "Request successful");
					Log.d("sendEntry(): REST-Request", "Response: "+responseBody);
					Log.d("sendEntry(): REST-Request", "Statuscode: "+statusCode);
					
					spinner.setVisibility(View.INVISIBLE);
					
					try {
						if(Integer.parseInt(responseBody) == 0)
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_successful), Toast.LENGTH_LONG).show();
						else if(Integer.parseInt(responseBody) == 1)
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_failed_doesNotExist), Toast.LENGTH_LONG).show();
						else if(Integer.parseInt(responseBody) == 2)
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_failed_articleNotStoredInYard), Toast.LENGTH_LONG).show();
						else if(Integer.parseInt(responseBody) == 3)
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_failed_articleAlreadyStored), Toast.LENGTH_LONG).show();
						else if(Integer.parseInt(responseBody) == 4)
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_failed_yardOccupied), Toast.LENGTH_LONG).show();
						else
							Toast.makeText(getApplicationContext(), getString(R.string.status_book_unexpected_result), Toast.LENGTH_LONG).show();
					} catch (NumberFormatException e) {
						Toast.makeText(getApplicationContext(), getString(R.string.status_book_unexpected_result), Toast.LENGTH_LONG).show();
						Log.e("sendEntry REST-Request", "Unexpected server response");
					}
					
				}

				@Override
				public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
					Log.e("sendEntry(): REST-Request", "Request failed");
					Log.e("sendEntry(): REST-Request", "Response: "+responseBody);
					Log.e("sendEntry(): REST-Request", "Statuscode: "+statusCode);
					
					spinner.setVisibility(View.INVISIBLE);
					
					if(statusCode == 403)
						Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
					else
						Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
				}
			});
		}
	}
}