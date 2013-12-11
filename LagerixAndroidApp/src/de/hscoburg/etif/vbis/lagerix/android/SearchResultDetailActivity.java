package de.hscoburg.etif.vbis.lagerix.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;

/**
 * Activity which displays the results of the search request in a list
 * @author Felix Lisczyk
 *
 */
public class SearchResultDetailActivity extends Activity {

	//Stores the retrieved article type
	ArticleTypeDTO articleType;
	
	//IP address from settings
	String baseURL;
	
	//UI elements
	TextView articleIdView;
	TextView articleNameView;
	TextView articleDescriptionView;
	TextView storageLocationsView;
	
	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		articleIdView = (TextView) findViewById(R.id.label_articleID_value);
		articleNameView = (TextView) findViewById(R.id.label_articleName_value);
		articleDescriptionView = (TextView) findViewById(R.id.label_articleDescription_value);
		storageLocationsView = (TextView) findViewById(R.id.label_storageLocations_value);

	}

	/**
	 * Gets called every time the activity appears on screen
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", "localhost:8080");
		
		Intent intent = getIntent();
		int articleTypeId = intent.getIntExtra("ARTICLE_TYPE_ID", 0);
		String articleName = intent.getStringExtra("ARTICLE_NAME");
		String articleDescription = intent.getStringExtra("ARTICLE_DESCRIPTION");

		articleIdView.setText(""+articleTypeId);
		articleNameView.setText(articleName);
		articleDescriptionView.setText(articleDescription);
		
		getStorageLocations(articleTypeId);
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

	private void getStorageLocations(int articleTypeId) {

		//Submit the search REST request
		LagerixRestClient.get(baseURL+"/lagerix/services/secure/android/storageLocations?articleTypeId="+articleTypeId, new JsonHttpResponseHandler() {

			// The REST request was successful.
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
				Log.d("Search REST-Request", "Response: "+response);
				Log.d("Search REST-Request", "Statuscode: "+statusCode);
				String storageLocations = "";
				try {
					for(int i = 0; i < response.length(); i++) {
						JSONObject object = response.getJSONObject(i);
						int id = object.getInt("id");
						storageLocations += id+"\n";
					}
				} catch(JSONException e) {
					Log.e("JSON-Exception", e.toString());
				}

				storageLocationsView.setText(storageLocations);

				
			}
			
			public void onFailure(int statusCode, java.lang.Throwable e, JSONObject errorResponse)  {
				Log.e("Search REST-Request", "Error: "+errorResponse);
				Log.e("Search REST-Request", "Statuscode: "+statusCode);
			}
		});
	}

}
