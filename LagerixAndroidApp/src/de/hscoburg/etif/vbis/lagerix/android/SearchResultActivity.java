package de.hscoburg.etif.vbis.lagerix.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		ListView list;
		String list_array[]={"Artikelart 1","Artikelart 2","Artikelart 3"};
		list=(ListView)findViewById(R.id.list_searchResults);
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,list_array));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
