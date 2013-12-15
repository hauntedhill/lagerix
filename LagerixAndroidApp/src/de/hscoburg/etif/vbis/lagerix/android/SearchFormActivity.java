package de.hscoburg.etif.vbis.lagerix.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Activity which displays a search form to the user
 * @author Felix Lisczyk
 *
 */
public class SearchFormActivity extends Activity {

	//UI elements
	EditText searchNameView;
	EditText searchDescriptionView;
	
	
	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_form);
		
		searchNameView = (EditText) findViewById(R.id.field_searchArticleName);
		searchDescriptionView = (EditText) findViewById(R.id.field_searchArticleDescription);
		
		// Show the keyboard if the first text field has focus
		searchNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});
		
		// Request focus to bring up the keyboard
		searchNameView.requestFocus();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
	 * Calls SearchResultActivity with values from the search form
	 */
	public void sendSearch(View view) {
    	Intent intent = new Intent(this, SearchResultActivity.class);
    	intent.putExtra("ARTICLE_NAME", searchNameView.getText().toString());
    	intent.putExtra("ARTICLE_DESCRIPTION", searchDescriptionView.getText().toString());
    	startActivity(intent);
	}

}
