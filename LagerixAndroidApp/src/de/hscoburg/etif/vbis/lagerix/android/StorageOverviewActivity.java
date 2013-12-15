package de.hscoburg.etif.vbis.lagerix.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;

public class StorageOverviewActivity extends Activity {


	//UI elements
	TextView yardIdView;
	TextView yardStatusView;
	ProgressBar spinner;
	LinearLayout resultLayout;
	
	//IP address from settings
	String baseURL;

	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storage_overview);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		yardIdView = (TextView) findViewById(R.id.label_yardIDResult);
		yardStatusView = (TextView) findViewById(R.id.label_yardStatusResult);
		
		spinner = new ProgressBar(this);		
		resultLayout = (LinearLayout) findViewById(R.id.layout_storageOverviewResult);
		resultLayout.addView(spinner, 1);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));
		getStorageOverview();
	}

	/**
	 * Listener method for the action bar buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {

		// Up button has been pressed
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void getStorageOverview() {

		//Submit the storage overview REST request
		LagerixRestClient.get(baseURL+getString(R.string.restURI_storageOverview), new JsonHttpResponseHandler() {

			// The REST request was successful.
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
				Log.d("getStorageOverview() REST-Request", "Response: "+response);
				Log.d("getStorageOverview() REST-Request", "Statuscode: "+statusCode);
				resultLayout.removeView(spinner);
				String yardIDs = "";
				String yardStatus = "";
				try {
					for(int i = 0; i < response.length(); i++) {
						JSONObject object = response.getJSONObject(i);
						int id = object.getInt("yardId");
						String status = object.getString("yardStatus");

						yardIDs += id+"\n";
						yardStatus += status+"\n";
					}
				} catch(JSONException e) {
					Log.e("JSON-Exception", e.toString());
					
					Toast.makeText(getApplicationContext(), getString(R.string.status_unexpected_result), Toast.LENGTH_LONG).show();
				}

				yardIdView.setText(""+yardIDs);
				yardStatusView.setText(yardStatus);


			}

			@Override
			public void onFailure(int statusCode, java.lang.Throwable e, JSONObject errorResponse)  {
				Log.e("getStorageOverview() REST-Request", "Error: "+errorResponse);
				Log.e("getStorageOverview() REST-Request", "Statuscode: "+statusCode);
				resultLayout.removeView(spinner);
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable e) {
				Log.e("getStorageOverview() REST-Request", "Error: "+responseBody);
				Log.e("getStorageOverview() REST-Request", "Statuscode: "+statusCode);
				resultLayout.removeView(spinner);
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
		});
	}

}
