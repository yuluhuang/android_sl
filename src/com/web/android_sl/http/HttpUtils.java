package com.web.android_sl.http;

import android.content.ContentUris;
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
import org.apache.http.client.methods.HttpGet;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		String result;
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

				result = EntityUtils.toString(response.getEntity(), "UTF-8");
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
	 * 判断是否为登录状态 暂未使用
	 */
	public void islogin() {
		final String path = "http://www.yuluhuang.com/ashx/LoginHandler.ashx?flag=islogin";
		String result;
		SessionService sessionService = new SessionService(
				ContextUtil.getInstance());
		String session = sessionService.getSession();

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);

		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {

				result = EntityUtils.toString(response.getEntity(), "UTF-8");
				String values = JsonTools.getKey("code", result);
				if (!values.equals("111111")) {
					login("1", "1");
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

	public static String getIconPath(String key) {
		final String path = "http://www.yuluhuang.com/ashx/qn_upload.ashx?flag=downToken&key="
				+ key;
		String values = "";
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

				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				values = JsonTools.getKey("key", result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}

	public static Bitmap getBitmapForIconPath(String iconPath) {
		Bitmap bitmap = null;
		InputStream inputStream = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(iconPath);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				byte[] data = EntityUtils.toByteArray(httpEntity);

				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public static String getTaskInfo(String themeID) {
		// TODO Auto-generated method stub
		String path = "http://www.yuluhuang.com/ashx/MyHomeHandler.ashx";

		String value = "";
		SessionService service = new SessionService(ContextUtil.getInstance());

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);

		HttpResponse response;
		try {
			// 3.构建请求实体的数据
			List<NameValuePair> paras = new ArrayList<NameValuePair>();
			paras.add(new BasicNameValuePair("flag", "zuopinfo"));
			paras.add(new BasicNameValuePair("id", themeID));
			// 4.构建实体
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paras,
					"utf-8");

			// 5.把实体放入请求对象
			httpPost.setEntity(entity);
			//设置cookie
			httpPost.setHeader("Cookie", service.getSession());
			// 6.执行请求
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {

				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				value =JsonTools.getKey("task",JsonTools.getKey("data", JsonTools.getKey("data", result)));
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}
}
