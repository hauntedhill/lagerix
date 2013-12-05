package de.hscoburg.etif.vbis.lagerix.android;

import java.security.KeyStore;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

public class LagerixRestClient {

	private static final String BASE_URL = "https://10.185.45.1:8181/lagerix/";
	private static AsyncHttpClient client;
	static {
		client = new AsyncHttpClient();
		try {
		      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		      trustStore.load(null, null);
		      LagerixSSLSocketFactory sf = new LagerixSSLSocketFactory(trustStore);
		      sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		      client.setSSLSocketFactory(sf);   
		    }
		    catch (Exception e) {   
		    }
	}

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
