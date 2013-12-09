package de.hscoburg.etif.vbis.lagerix.android;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Activity which displays the results of the search request in a list
 * @author Felix Lisczyk
 *
 */
public class SearchResultActivity extends ListActivity {

	//Stores retrieved article types
	LinkedList<String> articleTypes;
	ArrayAdapter<String> adapter;
	
	//IP address from settings
	String baseURL;
	
	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		articleTypes = new LinkedList<String>();

		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,articleTypes);
		setListAdapter(adapter);

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
		String articleName = intent.getStringExtra("ARTICLE_NAME");
		String articleDescription = intent.getStringExtra("ARTICLE_DESCRIPTION");
		searchArticleTypes(articleName, articleDescription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

	private void searchArticleTypes(String articleName, String articleDescription) {

		//Submit the search REST request
		LagerixRestClient.get(baseURL+"/lagerix/services/secure/android/search?name="+articleName+"&description="+articleDescription, new JsonHttpResponseHandler() {

			// The REST request was successful.
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
				Log.d("Search REST-Request", "Response: "+response);
				Log.d("Search REST-Request", "Statuscode: "+statusCode);
				try {
					for(int i = 0; i < response.length(); i++) {
						JSONObject object = response.getJSONObject(i);
						String name = object.getString("name");
						articleTypes.add(name);
					}
				} catch(JSONException e) {
					Log.e("JSON-Exception", e.toString());
				}

				adapter.notifyDataSetChanged();

				
			}
			
			public void onFailure(int statusCode, java.lang.Throwable e, JSONObject errorResponse)  {
				Log.e("Search REST-Request", "Error: "+errorResponse);
				Log.e("Search REST-Request", "Statuscode: "+statusCode);
			}
		});
	}

}
