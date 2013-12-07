package de.hscoburg.etif.vbis.lagerix.android;

import java.security.KeyStore;

import org.apache.http.client.params.ClientPNames;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

public class LagerixRestClient {

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
		client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(url, params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(url, params, responseHandler);
	}

	public static void addHeader(String header, String value) {
		client.addHeader(header, value);
	}

	public static void removeHeader(String header) {
		client.removeHeader(header);
	}

}
