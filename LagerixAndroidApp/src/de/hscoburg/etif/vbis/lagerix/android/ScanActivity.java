package de.hscoburg.etif.vbis.lagerix.android;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
	TextView article_result;
	TextView location_result;
	RadioButton bookedIn;
	TextView restResult;
	
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

		article_result = (TextView) findViewById(R.id.label_articleIDResult);
		location_result = (TextView) findViewById(R.id.label_storageIDResult);
		
		bookedIn = (RadioButton) findViewById(R.id.radio_bookedIn);
		
		restResult = (TextView) findViewById(R.id.label_restResult);
		
	}
	
	/**
	 * Gets called every time the activity appears on screen
	 */
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", "localhost:8080");
	}
	
	/**
	 * Initializer method for the action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_actions, menu);
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
	
	/**
	 * Opens the barcode scanning activity
	 * Gets called from UI button "Scan barcode"
	 * @param view
	 */
	public void scanBarcode(View view) {

		try {

			Intent intent = new Intent(
					"com.google.zxing.client.android.SCAN");
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
					article_result.setText(result.substring(1));
				
				//Storage barcodes start withe the letter 'S'
				else if(result.charAt(0) == 'S')
					location_result.setText(result.substring(1));
			}
		}
	}
	
	/**
	 * Sends a REST request containing the barcode data and selected UI elements
	 * Gets called from UI button "Send data"
	 * @param view
	 */
	public void sendEntry(View view) {
		if(article_result.getText().length() != 0 && location_result.getText().length() != 0) {
			
			// Create parameters for the REST request
			RequestParams params = new RequestParams();
			params.put("articleID", article_result.getText().toString());
			params.put("locationID", location_result.getText().toString());
			if(bookedIn.isChecked())
				params.put("bookedIn", "true");
			else
				params.put("bookedIn", "false");
			params.put("timestamp", ""+Calendar.getInstance().getTimeInMillis());
			
			// Call the REST helper class and send the request
			LagerixRestClient.post(baseURL+"/lagerix/services/secure/android/saveEntry", params, new TextHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody) {
					restResult.setText("Erfolg!!!\nStatuscode: "+statusCode+"\nResponse: \n"+responseBody);
				}
				
				@Override
				public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable error) {
					restResult.setText("Fehler!!!\nStatuscode: "+statusCode+"\nResponse: \n"+responseBody+"\nError: "+error);
				}
			});
		}
	}
}