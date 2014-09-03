package com.web.android_sl.http;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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

import com.web.android_sl.login;
import com.web.android_sl.db.SessionService;
import com.web.android_sl.json.JsonTools;
import com.web.android_sl.utils.ContextUtil;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity.Header;
import android.util.Log;
import android.widget.Toast;

public class HttpUtils {
	public HttpUtils(Context content) {

	}

	/**
	 * 登录获取session 保存数据库
	 * 
	 * @param path
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean login(String userName, String password) {
		final String path = "http://www.yuluhuang.com/ashx/LoginHandler.ashx?flag=login";
		String sessionId = "";
		SessionService sessionService = new SessionService(
				ContextUtil.getInstance());

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
					String values = JsonTools.getKey("code", result);
					Log.i("string", values);
					// 登录成功
					if (values.equals("000000")) {

						org.apache.http.Header[] cookie = response
								.getHeaders("Set-Cookie");
						sessionId = cookie[0].getValue().toString().split(";")[0]
								.toString();
						Log.i("string", sessionId);
						// return sessionId;

						boolean has = sessionService.hasSession("1");
						long cursorId;
						if (has) {// 是否为空表，如果有记录，则更新
							// 数据库操作不能在子线程中？
							cursorId = sessionService.update(sessionId, "1");
						} else {// 否则插入
							cursorId = sessionService.save(sessionId);
						}
						if (cursorId > 0) {
							return true;
						}
					}
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
		return false;
	}

	
	public static String getUserInfo() {
		final String path = "http://www.yuluhuang.com/ashx/MyHomeHandler.ashx?flag=myhome";
		String  result;
		SessionService sessionService = new SessionService(
				ContextUtil.getInstance());
		String session = sessionService.getSession();

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		httpPost.setHeader("Cookie", session);

		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {

				 result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				 String values = JsonTools.getKey("data", result);
					Log.i("string", values);
				return values;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 判断是否为登录状态  暂未使用
	 */
	public void islogin(){
		final String path = "http://www.yuluhuang.com/ashx/LoginHandler.ashx?flag=islogin";
		String  result;
		SessionService sessionService = new SessionService(
				ContextUtil.getInstance());
		String session = sessionService.getSession();

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);

		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {

				 result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				 String values = JsonTools.getKey("code", result);
					if(!values.equals("111111")){
						login("1","1");
					}
			
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
