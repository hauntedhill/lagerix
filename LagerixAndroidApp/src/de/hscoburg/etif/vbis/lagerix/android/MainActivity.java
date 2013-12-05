package de.hscoburg.etif.vbis.lagerix.android;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends Activity {
	TextView article_result;
	TextView location_result;
	RadioButton bookedIn;
	String userID = "1111";
	TextView restResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		article_result = (TextView) findViewById(R.id.label_articleIDResult);
		location_result = (TextView) findViewById(R.id.label_storageIDResult);
		
		bookedIn = (RadioButton) findViewById(R.id.radio_bookedIn);
		
		restResult = (TextView) findViewById(R.id.label_restResult);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	Intent intent = new Intent(this, SearchFormActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void scanBarcode(View view) {

		try {

			Intent intent = new Intent(
					"com.google.zxing.client.android.SCAN");
			intent.putExtra("RESULT_DISPLAY_DURATION_MS", 0l);
			startActivityForResult(intent, 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "ERROR:" + e, 1).show();

		}
	}

	//In the same activity you’ll need the following to retrieve the results:
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {

			if (resultCode == RESULT_OK) {
				String result = intent.getStringExtra("SCAN_RESULT");
				if(result.charAt(0) == 'A')
					article_result.setText(result.substring(1));
				else if(result.charAt(0) == 'S')
					location_result.setText(result.substring(1));
			}
		}
	}
	
	public void sendEntry(View view) {
		if(article_result.getText().length() != 0 && location_result.getText().length() != 0) {
			RequestParams params = new RequestParams();
			params.put("articleID", article_result.getText().toString());
			params.put("locationID", location_result.getText().toString());
			if(bookedIn.isChecked())
				params.put("bookedIn", "true");
			else
				params.put("bookedIn", "false");
			params.put("userID", userID);
			params.put("timestamp", ""+Calendar.getInstance().getTimeInMillis());
			LagerixRestClient.post("services/secure/book/saveEntry", params, new TextHttpResponseHandler() {
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