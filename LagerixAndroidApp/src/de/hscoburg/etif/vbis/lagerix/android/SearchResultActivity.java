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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import de.hscoburg.etif.vbis.lagerix.android.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.android.helper.LagerixRestClient;

/**
 * Activity which displays the results of the search request in a list
 * @author Felix Lisczyk
 *
 */
public class SearchResultActivity extends ListActivity {

	//Stores retrieved article types
	LinkedList<ArticleTypeDTO> articleTypes;
	ArrayAdapter<ArticleTypeDTO> adapter;
	
	//IP address from settings
	String baseURL;
	
	//UI elements
	LinearLayout resultLayout;
	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		resultLayout = (LinearLayout) findViewById(R.id.layout_resultList);

		articleTypes = new LinkedList<ArticleTypeDTO>();

		adapter = new ArrayAdapter<ArticleTypeDTO>(this,android.R.layout.simple_list_item_1 ,articleTypes);
		setListAdapter(adapter);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		baseURL = sharedPref.getString("server_ip", getString(R.string.ipAddress_default));
		
		Intent intent = getIntent();
		String articleName = intent.getStringExtra("ARTICLE_NAME");
		String articleDescription = intent.getStringExtra("ARTICLE_DESCRIPTION");
		if(articleTypes.size() == 0)
			searchArticleTypes(articleName, articleDescription);

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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ArticleTypeDTO articleType = (ArticleTypeDTO) getListView().getAdapter().getItem(position);
		Intent intent = new Intent(this, SearchResultDetailActivity.class);
		intent.putExtra("articleType", articleType);
    	startActivity(intent);
	}
	
	/**
	 * Performs a REST request to search for article types using the supplied search keys
	 * @param articleName Name of the article type
	 * @param articleDescription Description of the article type
	 */

	private void searchArticleTypes(String articleName, String articleDescription) {

		//Submit the search REST request
		LagerixRestClient.get(baseURL+getString(R.string.restURI_search)+"?name="+articleName+"&description="+articleDescription, new JsonHttpResponseHandler() {

			// The REST request was successful.
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONArray response) {
				Log.d("Search REST-Request", "Response: "+response);
				Log.d("Search REST-Request", "Statuscode: "+statusCode);
				if(response.length() > 0) {
					// The search returned objects matching the search criterias.
					try {
						for(int i = 0; i < response.length(); i++) {
							JSONObject object = response.getJSONObject(i);
							int id = object.getInt("id");
							String name = object.getString("name");
							String description = object.getString("description");
							articleTypes.add(new ArticleTypeDTO(id, name, description));
						}
					} catch(JSONException e) {
						Log.e("JSON-Exception", e.toString());
						
						// Hide the progess indicator
						resultLayout.setVisibility(View.INVISIBLE);
						
						Toast.makeText(getApplicationContext(), getString(R.string.status_unexpected_result), Toast.LENGTH_LONG).show();
					}
					
					// Update the list view
					adapter.notifyDataSetChanged();
				}
				else {
					// The search returned no results
					// Hide the progess indicator
					resultLayout.setVisibility(View.INVISIBLE);
					
					Toast.makeText(getApplicationContext(), getString(R.string.status_no_searchResults), Toast.LENGTH_LONG).show();
					
				}

				
			}
			
			@Override
			public void onFailure(int statusCode, java.lang.Throwable e, JSONObject errorResponse)  {
				Log.e("searchArticleTypes() REST-Request", "Error: "+errorResponse);
				Log.e("searchArticleTypes() REST-Request", "Statuscode: "+statusCode);
				
				// Hide the progess indicator
				resultLayout.setVisibility(View.INVISIBLE);
				
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.String responseBody, java.lang.Throwable e) {
				Log.e("searchArticleTypes() REST-Request", "Error: "+responseBody);
				Log.e("searchArticleTypes() REST-Request", "Statuscode: "+statusCode);
				
				// Hide the progess indicator
				resultLayout.setVisibility(View.INVISIBLE);
				
				if(statusCode == 403)
					Toast.makeText(getApplicationContext(), getString(R.string.status_not_authorized), Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), getString(R.string.status_communication_error), Toast.LENGTH_LONG).show();
			}
		});
	}

}
