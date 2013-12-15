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
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
	TextView yardsView;
	ProgressBar spinner;
	LinearLayout resultLayout;
	
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
		yardsView = (TextView) findViewById(R.id.label_yards_value);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));
		
		Intent intent = getIntent();
		ArticleTypeDTO articleType = (ArticleTypeDTO) intent.getSerializableExtra("articleType");

		articleIdView.setText(""+articleType.getId());
		articleNameView.setText(articleType.getName());
		articleDescriptionView.setText(articleType.getDescription());
		
		spinner = new ProgressBar(this);		
		resultLayout = (LinearLayout) findViewById(R.id.layout_searchResultDetailResult);
		resultLayout.addView(spinner, 0);
		
		getYards(articleType.getId());

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

	/**
	 * Performs a REST request to get all yards, where an article type is stored in
	 * The results are displayed in yardsView
	 * @param articleTypeId ID of the article type
	 */
	private void getYards(int articleTypeId) {

		//Submit the search REST request
		LagerixRestClient.get(baseURL+getString(R.string.restURI_yards)+"?articleTypeId="+articleTypeId, new JsonHttpResponseHandler() {

			// The REST request was successful.
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
				Log.d("getYards() REST-Request", "Response: "+response);
				Log.d("getYards() REST-Request", "Statuscode: "+statusCode);
				resultLayout.removeView(spinner);
				String yards = "";
				try {
					for(int i = 0; i < response.length(); i++) {
						JSONObject object = response.getJSONObject(i);
						int id = object.getInt("id");
						yards += id+"\n";
					}
				} catch(JSONException e) {
					Log.e("JSON-Exception", e.toString());
					
					Toast.makeText(getApplicationContext(), getString(R.string.status_unexpected_result), Toast.LENGTH_LONG).show();
				}

				yardsView.setText(yards);

				
			}
			
			// The REST request failed.
			@Override
			public void onFailure(int statusCode, java.lang.Throwable e, JSONObject errorResponse)  {
				Log.e("getYards() REST-Request", "Error: "+errorResponse);
				Log.e("getYards() REST-Request", "Statuscode: "+statusCode);
				
				// Hide the progess indicator
				resultLayout.removeView(spinner);
				
				//Show an error message depending on the status code
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
			
			// The REST request failed.
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable e) {
				Log.e("getYards() REST-Request", "Error: "+responseBody);
				Log.e("getYards() REST-Request", "Statuscode: "+statusCode);
				
				// Hide the progess indicator
				resultLayout.removeView(spinner);
				
				//Show an error message depending on the status code
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
		});
	}

}
