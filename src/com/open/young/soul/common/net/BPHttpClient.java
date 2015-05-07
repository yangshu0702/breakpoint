package com.open.young.soul.common.net;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.open.young.soul.common.util.BPCommonUtils;
import com.open.young.soul.common.util.BPLogger;

/**
 * 基于Apache Httpclient建立HTTP请求工具类
 * 
 * @author breakpoint
 * 
 */
public class BPHttpClient {
	/**
	 * 日志tag
	 */
	private static final String TAG = BPHttpClient.class.getSimpleName();

	/**
	 * 连接请求池的超时时间
	 */
	public static final int DEFAULT_CONNTCT_POOL_TIMEOUT = 1000 * 3;

	/**
	 * HTTP连接超时时间
	 */
	public static final int DEFAULT_HTTP_CONNTCT_TIMEOUT = 1000 * 10;

	/**
	 * socket连接超时时间
	 */
	public static final int DEFAULT_SOCKET_TIMEOUT = 1000 * 10;

	private static HttpClient mHttpClient;

	private static ArrayList<HttpRequestBase> mRequestList = new ArrayList<>();

	private static BPHttpClient sBpHttpClient;

	private BPHttpClient() {

	}

	public static BPHttpClient instance() {
		if (null == sBpHttpClient) {
			sBpHttpClient = new BPHttpClient();
		}
		init(DEFAULT_CONNTCT_POOL_TIMEOUT, DEFAULT_HTTP_CONNTCT_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
		return sBpHttpClient;
	}
	

	/**
	 * 对于超时时间的初始化，方便进行动态配置
	 * 
	 * @param httpPoolConnTime
	 * @param httpConnTime
	 * @param socketConnTime
	 */
	public static void init(int httpPoolConnTime, int httpConnTime,
			int socketConnTime) {
		if (null != mHttpClient) {
			BPLogger.w(TAG,
					"HttpClient has been inited,do not init do not init again.");
			return;
		}
		if (httpPoolConnTime <= 0 || httpConnTime <= 0 || socketConnTime <= 0) {
			BPLogger.w(TAG,
					"Init TimeOut time can not be below 0, use default value instead.");

			httpPoolConnTime = DEFAULT_CONNTCT_POOL_TIMEOUT;
			httpConnTime = DEFAULT_HTTP_CONNTCT_TIMEOUT;
			socketConnTime = DEFAULT_SOCKET_TIMEOUT;
		}
		HttpParams httpParams = new BasicHttpParams();
		ConnManagerParams.setTimeout(httpParams, httpPoolConnTime);
		HttpConnectionParams.setConnectionTimeout(httpParams, httpConnTime);
		HttpConnectionParams.setSoTimeout(httpParams, socketConnTime);
		
		//设置tcp_nodelay为true，是因为，单个请求一般速率很快，希望保证实时性，如果设置为false，虽然提高了信道带宽的利用率，但是在这样的场景下不必要，下载功能中应该考虑设置为false
		HttpConnectionParams.setTcpNoDelay(httpParams, true);

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
		HttpProtocolParams.setUserAgent(httpParams, System.getProperties()
				.getProperty("http.agent"));

		// scheme: http and https
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				httpParams, schemeRegistry);

		mHttpClient = new DefaultHttpClient(manager, httpParams);
	}

	public void get(String url, List<BasicNameValuePair> params,
			BPHttpCallback callback) {
		get(url, params,null, callback);
	}

	public void get(final String url, final List<BasicNameValuePair> params,
			final List<Header> headerList, final BPHttpCallback callback) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				if (BPCommonUtils.isNullOrEmptyWithTrim(url)) {
					// TODO URL为空的response
					Log.e(TAG, "url is null or empty");
					callback.onError(BPRespCode.CODE_URL_EMPTY);
					return;
				}
				String requestUrl = url;
				if (null != params) {
					requestUrl += (URLEncodedUtils.format(params, HTTP.UTF_8) + "?");
				}
				Log.i(TAG, "get url = " + requestUrl);
				final HttpGet httpGet = new HttpGet(requestUrl);
				if (null != headerList) {
					for (Header header : headerList) {
						Log.i(TAG, "get header " + header.getName() +  " = " + header.getValue());
						httpGet.addHeader(header);
					}
				}
				try {
					mRequestList.add(httpGet);
					mHttpClient.execute(httpGet, new ResponseHandler<HttpResponse>() {

						@Override
						public HttpResponse handleResponse(HttpResponse httpResponse)
								throws ClientProtocolException, IOException {
							mRequestList.remove(httpGet);
							int reponseCode = httpResponse.getStatusLine()
									.getStatusCode();
							switch (reponseCode) {
							case HttpStatus.SC_OK:
								String response = new String(EntityUtils
										.toByteArray(httpResponse.getEntity()), Charset
										.forName(HTTP.UTF_8));
								callback.onResponse(response);
								break;
							default:
								callback.onError(reponseCode);
								break;
							}
							return httpResponse;
						}
					});
				} catch (ClientProtocolException e) {
					mRequestList.remove(httpGet);
					Log.e(TAG, "request " + url + "happens error e = " + e.toString());
				} catch (IOException e) {
					mRequestList.remove(httpGet);
					Log.e(TAG, "request " + url + "happens error e = " + e.toString());
				}
			}
		}).start();
	}

}
