package de.hscoburg.etif.vbis.lagerix.android.helper;

import java.security.KeyStore;

import org.apache.http.client.params.ClientPNames;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

/**
 * Helper class which performs REST requests
 * @author Felix Lisczyk
 *
 */
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

	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(url, responseHandler);
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
