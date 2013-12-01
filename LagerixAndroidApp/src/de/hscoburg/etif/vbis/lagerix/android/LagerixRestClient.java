package de.hscoburg.etif.vbis.lagerix.android;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LagerixRestClient {

	private static final String BASE_URL = "http://10.185.45.1:8080/lagerix/services/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void addHeader(String header, String value) {
		client.addHeader(header, value);
	}

	public static void removeHeader(String header) {
		client.removeHeader(header);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

}
