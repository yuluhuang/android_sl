package com.web.android_sl.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import com.web.android_sl.json.JsonTools;

import android.R.integer;
import android.preference.PreferenceActivity.Header;
import android.util.Log;

public class HttpUtils {

	// public static void myHttpPost(List<Map<String, String>> list){
	// //
	// HttpClient httpClient=new DefaultHttpClient();
	// HttpPost httppost=new HttpPost(path);
	// List<NameValuePair> params=new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("", value))
	//
	//
	// }

	public static void login(String path, String userName, String password) {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(path);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userName", userName));
			params.add(new BasicNameValuePair("password", password));

			UrlEncodedFormEntity entity;

			entity = new UrlEncodedFormEntity(params, "utf-8");

			httpPost.setEntity(entity);

			HttpResponse response;
			try {
				response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() == 200) {
					
					String result = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					Log.i("string", result);
					String value=JsonTools.getKey("code", result);
					//登录成功
					if(value=="000000"){
						org.apache.http.Header[] cookie = response
								.getHeaders("Set-Cookie");

						String aString = cookie[0].toString();
						Log.i("string", aString);
					}
					



					// org.apache.http.Header[] B=response.getAllHeaders();
					//HttpEntity httpEntity = response.getEntity();
					// InputStream inputStream =
					// response.getEntity().getContent();
					

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
