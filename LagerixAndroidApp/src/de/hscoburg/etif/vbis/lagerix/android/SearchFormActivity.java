package de.hscoburg.etif.vbis.lagerix.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * Activity which displays a search form to the user
 * @author Felix Lisczyk
 *
 */
public class SearchFormActivity extends Activity {


	/**
	 * Initializer method for the activity
	 * Gets called on first launch
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_form);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Initializer method for the action bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_form, menu);
		return true;
	}
	
	public void sendSearch(View view) {
    	Intent intent = new Intent(this, SearchResultActivity.class);
    	EditText articleName = (EditText) findViewById(R.id.field_searchArticleName);
    	EditText articleDescription = (EditText) findViewById(R.id.field_searchArticleDescription);
    	intent.putExtra("ARTICLE_NAME", articleName.getText().toString());
    	intent.putExtra("ARTICLE_DESCRIPTION", articleDescription.getText().toString());
    	startActivity(intent);
	}

}
